package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLKugel;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.ObjectHolder;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.collider.SphereCollider;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/5/14.
 */
public class Sphere extends VisibleTransform
{

    public Sphere(Vector position, double radius, Scene scene, String tag, String name) throws Exception
    {
        super(position, scene, tag, name);
        backend = new GLKugel(position.toGLVektor(),radius);
        defaultScale[0] = radius;
        defaultScale[1] = radius;
        defaultScale[2] = radius;
    }

    public Sphere(Vector position, double radius, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        super(position, scene, parent, tag, name);
        backend = new GLKugel(position.toGLVektor(),radius);
    }

    @Override
    public void rotate(Vector degr)
    {
        backend.drehe(degr.getX(),degr.getY(),degr.getZ());
        for(ObjectHolder t : getChildren())
        {
            if( t instanceof Transform)
            {
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(1,0,0),(float)degr.getX());
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(0,1,0),(float)degr.getY());
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(0,0,1),(float)degr.getZ());
            }
        }
    }

    @Override
    public void rotateAround(double posX, double posY, double posZ, double axisX, double axisY, double axisZ, float degrees)
    {
        backend.rotiere(degrees,axisX,axisY,axisZ,posX,posY,posZ);
        for(ObjectHolder t : getChildren())
        {
            if( t instanceof Transform)
            {
                ((Transform) t).rotateAround(posX,posY,posZ,axisX,axisY,axisZ,degrees);
            }
        }
    }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees)
    {
        rotateAround(position.getX(),position.getY(),position.getZ(),direction.getX(),direction.getY(),direction.getZ(),degrees);
    }

    @Override
    protected void updateBackend()
    {
        backend.setzePosition(getAbsolutePosition().toGLVektor());
    }

    public double getBiggestRadius()
    {
        double biggest = 0;
        if(getDefaultScaleX() > biggest) biggest = getDefaultScaleX();
        if(getDefaultScaleY() > biggest) biggest = getDefaultScaleY();
        if(getDefaultScaleZ() > biggest) biggest = getDefaultScaleZ();
        return biggest;
    }

    @Override
    public void createCollider()
    {
        collider = new SphereCollider(this,getBiggestRadius());
        System.out.println(((SphereCollider)collider).getRadius());
    }

    @Override
    protected boolean destroyBackend() {
        backend.loesche();
        return true;
    }
}