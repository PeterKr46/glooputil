package com.gmail.pkr4mer.glooputil.object.collider;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;


/**
 * Created by peter on 1/28/14.
 */

public final class GUSphereCollider extends GUCollider
{
    private double radius;

    public GUSphereCollider(Transform object, double radius)
    {
        super(object);
        setRadius(radius);
        setCenterOffset(new Vector(0, 0, 0));
    }

    public GUSphereCollider(Transform object, double radius, Vector offset)
    {
        super(object);
        setRadius(radius);
        setCenterOffset(offset);
    }

    @Override
    public boolean contains(Vector v)
    {
        return getGUObject().getPosition().distanceSquared(v) <= getRadius()*getRadius();
    }

    @Override
    protected boolean checkSphereCollision(GUSphereCollider other)
    {
        return getAbsoluteCenter().distanceSquared(other.getAbsoluteCenter()) <= Math.pow(radius+other.radius,2);
    }

    @Override
    protected boolean checkCylinderCollision(GUCylinderCollider other) { //TODO
        return false;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public double getRadius()
    {
        return radius;
    }
}
