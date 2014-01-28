package com.gmail.pkr4mer.glooputil.object.collider;

import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/28/14.
 */
public final class GUBoxCollider extends GUCollider
{

    private double width,height,length;

    public GUBoxCollider(GUObject guo, double width, double height, double length)
    {
        super(guo);
    }

    @Override
    public boolean contains(Vector v)
    {
        Vector l = getCube().getLowEdge();
        Vector h = getCube().getHighEdge();
        return ((v.getY()>=l.getY()&&v.getY()<=h.getY()) &&
                (v.getZ()>=l.getZ()&&v.getZ()<=h.getZ()) &&
                (v.getX()>=l.getX()&&v.getX()<=h.getX())
        );
    }

    public GUCube getCube()
    {
        return (GUCube) getGUObject();
    }

    @Override
    protected boolean checkSphereCollision(GUSphereCollider other)
    {
        return false;
    }

    @Override
    protected boolean checkCylinderCollision(GUCylinderCollider other)
    {
        return false;
    }

    @Override
    protected boolean checkBoxCollision(GUBoxCollider other)
    {
        return false;
    }
}
