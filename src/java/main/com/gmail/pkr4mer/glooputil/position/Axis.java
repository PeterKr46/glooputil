package com.gmail.pkr4mer.glooputil.position;

/**
 * Created by peter on 1/24/14.
 */
public enum Axis
{
    X,Y,Z;

    public Vector toVector()
    {
        switch(this)
        {
            case X: return new Vector(1,0,0);
            case Y: return new Vector(0,1,0);
            case Z: return new Vector(0,0,1);
            default: return new Vector(1,0,0);
        }
    }
}
