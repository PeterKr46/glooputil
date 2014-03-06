package com.gmail.pkr4mer.glooputil.object.collider;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/28/14.
 */
public final class CylinderCollider extends Collider
{
    private double radius;
    private double height;

    public CylinderCollider(Transform object, double radius, double height)
    {
        super(object);
        System.out.println("Radius: " + radius + " Height: " + height);
        setRadius(radius);
        setHeight(height);
    }

    public CylinderCollider(Transform object, double radius, double height, Vector offset)
    {
        super(object);
        setRadius(radius);
        setHeight(height);
        setCenterOffset(offset);
    }

    @Override
    public boolean contains(Vector v)
    {
        if(v.getY() > getAbsoluteCenter().getY() + height/2) return false;
        if(v.getY() < getAbsoluteCenter().getY() - height/2) return false;
        Vector a = getAbsoluteCenter();
        a.setY(0);
        Vector b = v.clone();
        b.setY(0);
        return a.distanceSquared(b) <= Math.pow(radius, 2);
    }

    @Override
    protected boolean checkSphereCollision(SphereCollider other)
    {
        if(other.getAbsoluteCenter().getY() > getAbsoluteCenter().getY() + height)
        {
            double diffY = other.getAbsoluteCenter().getY() - getAbsoluteCenter().getY();
            if(diffY > other.getRadius())
            {
                return false;
            }
            double loweredSphereRadius = Math.pow(other.getRadius(),2) - diffY*diffY;
            Vector loweredSphereCenter = other.getAbsoluteCenter();
            loweredSphereCenter.add(new Vector(0,-diffY,0));
            double distSqrd = getUpperCenter().distanceSquared(loweredSphereCenter);
            return Math.pow(loweredSphereRadius+getRadius(),2) < distSqrd;
        }
        else if(other.getAbsoluteCenter().getY() < getAbsoluteCenter().getY() - height)
        {
            double diffY = getAbsoluteCenter().getY() - other.getAbsoluteCenter().getY();
            if(diffY > other.getRadius())
            {
                return false;
            }
            double raisedSphereRadius = Math.pow(other.getRadius(),2) - diffY*diffY;
            Vector raisedSphereCenter = other.getAbsoluteCenter();
            raisedSphereCenter.add(new Vector(0,diffY,0));
            double distSqrd = getUpperCenter().distanceSquared(raisedSphereCenter);
            return Math.pow(raisedSphereRadius+getRadius(),2) < distSqrd;
        }
        else
        {
            Vector a = getAbsoluteCenter();
            a.setY(0);
            Vector b = other.getAbsoluteCenter();
            b.setY(0);
            return a.distanceSquared(b) <= Math.pow(radius+other.getRadius(),2);
        }
    }

    @Override
    protected boolean checkCylinderCollision(CylinderCollider other)
    {
        if(
                other.getLowerCenter().getY() <= getUpperCenter().getY() &&
                other.getLowerCenter().getY() >= getLowerCenter().getY() )
        {
            Vector a = getAbsoluteCenter();
            a.setY(0);
            Vector b = other.getAbsoluteCenter();
            b.setY(0);
            return a.distanceSquared(b) <= Math.pow(radius+other.radius,2);
        }
        else if(
                other.getUpperCenter().getY() <= getUpperCenter().getY() &&
                other.getUpperCenter().getY() >= getLowerCenter().getY() )
        {
            Vector a = getAbsoluteCenter();
            a.setY(0);
            Vector b = other.getAbsoluteCenter();
            b.setY(0);
            return a.distanceSquared(b) <= Math.pow(radius+other.radius,2);
        }
        return false;
    }

    @Override
    protected boolean checkBoxCollision(BoxCollider other)
    {
        return other.checkCylinderCollision(this);
    }

    public Vector getUpperCenter()
    {
        Vector u = getAbsoluteCenter();
        u.add(new Vector(0,height/2,0));
        return u;
    }

    public Vector getLowerCenter()
    {
        Vector u = getAbsoluteCenter();
        u.add(new Vector(0, -(height / 2), 0));
        return u;
    }
      
    public double getRadius()
    {
        return radius;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}