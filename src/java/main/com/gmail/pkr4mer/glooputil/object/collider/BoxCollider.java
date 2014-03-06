package com.gmail.pkr4mer.glooputil.object.collider;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 3/6/14.
 */
public class BoxCollider extends Collider
{
    private double width, height, length;

    public BoxCollider(Transform guo, double radius)
    {
        super(guo);
        width = radius*2;
        height = radius*2;
        length = radius*2;
    }

    public Vector[] getCorners()
    {
        Vector[] corners = new Vector[8];
        corners[0] = getAbsoluteCenter().add(width/2,height/2,length/2);
        corners[1] = getAbsoluteCenter().add(-width/2,-height/2,-length/2);
        corners[2] = getAbsoluteCenter().add(width/2,height/2,-length/2);
        corners[3] = getAbsoluteCenter().add(width/2,-height/2,length/2);
        corners[4] = getAbsoluteCenter().add(-width/2,height/2,length/2);
        corners[5] = getAbsoluteCenter().add(-width/2,-height/2,length/2);
        corners[6] = getAbsoluteCenter().add(width/2,-height/2,-length/2);
        corners[7] = getAbsoluteCenter().add(-width/2,height/2,-length/2);
        return corners;
    }

    @Override
    public boolean contains(Vector v)
    {
        Vector pos = getAbsoluteCenter();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        boolean xMatches = v.getX() >= x - (0.5*width) && v.getX() <= x + (0.5*width);
        boolean yMatches = v.getY() >= y - (0.5*height) && v.getY() <= y + (0.5*height);
        boolean zMatches = v.getZ() >= z - (0.5*length) && v.getZ() <= z + (0.5*length);
        return xMatches && yMatches && zMatches;
    }

    @Override
    protected boolean checkSphereCollision(SphereCollider other)
    {
        Vector diff = other.getAbsoluteCenter().difference(getAbsoluteCenter());
        // Center of Cube is less than Sphere's radius away from Sphere's center.
        if(diff.magnitude() < other.getRadius()) return true;
        diff.setMagnitude(other.getRadius());
        // Closest point on Sphere's shell is contained by Box
        Vector closest = other.getAbsoluteCenter().add(diff);
        return contains(closest);
    }

    @Override
    protected boolean checkCylinderCollision(CylinderCollider other)
    {
        // Above or below
        if(other.getLowerCenter().getY() > getAbsoluteCenter().add(0,height*0.5,0).getY()) return false;
        if(other.getUpperCenter().getY() < getAbsoluteCenter().add(0,-height*0.5,0).getY()) return false;
        Vector otherCenter = other.getAbsoluteCenter();
        //TODO alles mögliche
        return true;
    }

    @Override
    protected boolean checkBoxCollision(BoxCollider other)
    {
        for(Vector v : getCorners())
        {
            if(other.contains(v)) return true;
        }
        for(Vector v : other.getCorners())
        {
            if(contains(v)) return true;
        }
        // TODO kanten
        return false;
    }
}
