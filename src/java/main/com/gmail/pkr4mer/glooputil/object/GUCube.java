package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLQuader;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/24/14.
 */
public class GUCube extends GUObject
{

    public GUCube(Scene scene, GLQuader g, String t, String n) {
        super(g, scene, t, n);
    }

    protected GLQuader getCube()
    {
        return (GLQuader) glo;
    }

    public Vector getLowEdge()
    {
        Vector p = getPosition();
        p.add(new Vector(-getWidth()/2,-getHeight()/2,-getLength()/2));
        return p;
    }

    public Vector getHighEdge()
    {
        Vector p = getPosition();
        p.add(new Vector(getWidth()/2,getHeight()/2,getLength()/2));
        return p;
    }

    public double getWidth()
    {
        return getScaleX();
    }

    public double getHeight()
    {
        return getScaleY();
    }

    public double getLength()
    {
        return getScaleZ();
    }

    public double cornerDistanceSquared()
    {
        return Math.pow(getWidth()/2,2) + Math.pow(getLength()/2,2) + Math.pow(getHeight()/2,2);
    }

    public double getLongestDistanceXZ()
    {
        return Math.sqrt(Math.pow(getWidth()/2,2) + Math.pow(getLength()/2,2));
    }

    public double cornerDistance()
    {
        return Math.sqrt(cornerDistanceSquared());
    }
}