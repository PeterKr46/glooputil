package com.gmail.pkr4mer.glooputil.object.collider;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 1/28/14.
 */

public abstract class GUCollider
{
    private List<GUCollider> collisions = new ArrayList<>();
    private Transform guo;
    private boolean valid = true;
    private Vector centerOffset;

    public GUCollider(Transform guo)
    {
        this.guo = guo;
        this.centerOffset = new Vector(0,0,0);
    }

    public Transform getGUObject()
    {
        return guo;
    }

    public abstract boolean contains(Vector v);

    public final boolean collidesWith(GUCollider other)
    {
        if(other == this) return false;
        if(other instanceof GUSphereCollider)
        {
            return checkSphereCollision((GUSphereCollider)other);
        }
        if(other instanceof GUCylinderCollider)
        {
            return checkCylinderCollision((GUCylinderCollider)other);
        }
        return false;
    }

    public void destroy()
    {
        valid = false;
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setCenterOffset(Vector centerOffset)
    {
        this.centerOffset = centerOffset;
    }

    public Vector getCenterOffset()
    {
        return centerOffset;
    }

    public Vector getAbsoluteCenter()
    {
        Vector v = getGUObject().getAbsolutePosition();
        v.add(getCenterOffset());
        return v;
    }

    protected abstract boolean checkSphereCollision(GUSphereCollider other);

    protected abstract boolean checkCylinderCollision(GUCylinderCollider other);


    public final void fixedUpdate()
    {
        if(!valid) return;
        List<GUCollider> newCollisions = new ArrayList<>();
        for(GUCollider c : getGUObject().getScene().getColliders())
        {
            if(collidesWith(c) || c.collidesWith(this))
            {
                newCollisions.add(c);
            }
        }
        for( GUCollider c : newCollisions )
        {
            if(!collisions.contains(c))
            {
                collisions.add(c);
                throwCollisionEnter(c);
            }
            else
            {
                throwCollisionStay(c);
            }
        }
        List<GUCollider> remove = new ArrayList<>();
        for( GUCollider c : collisions )
        {
            if(!newCollisions.contains(c))
            {
                throwCollisionExit(c);
                remove.add(c);
            }
        }
        for( GUCollider c : remove )
        {
            collisions.remove(c);
        }
    }

    protected final void throwCollisionStay(GUCollider c)
    {
        for(String scriptType : getGUObject().getScripts())
        {
            getGUObject().getScript(scriptType).onCollisionStay(c);
        }
    }

    protected final void throwCollisionEnter(GUCollider c)
    {
        for(String scriptType : getGUObject().getScripts())
        {
            getGUObject().getScript(scriptType).onCollisionEnter(c);
        }
    }

    protected final void throwCollisionExit(GUCollider c)
    {
        for(String scriptType : getGUObject().getScripts())
        {
            getGUObject().getScript(scriptType).onCollisionExit(c);
        }
    }
}