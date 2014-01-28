package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLKugel;
import com.gmail.pkr4mer.glooputil.Scene;

/**
 * Created by peter on 1/24/14.
 */
public class GUEllipsoid extends GUObject
{

    public GUEllipsoid(Scene scene, GLKugel sphere, String tag, String name) {
        super(sphere, scene, tag, name);
    }

    protected GLKugel getSphere()
    {
        return (GLKugel) glo;
    }

    public void setQuality(int q)
    {
        getSphere().setzeQualitaet(q);
    }

    public double getRadX()
    {
        return defaultScales[0]*0.5*getScaleX();
    }

    public double getRadY()
    {
        return defaultScales[1]*0.5*getScaleY();
    }

    public double getRadZ()
    {
        return defaultScales[2]*0.5*getScaleZ();
    }

    public double getBiggestRadius()
    {
        double x = getRadX();
        double y = getRadY();
        double z = getRadZ();
        if(x > y)
        {
            if( x > z )
            {
                return x;
            }
            return z;
        }
        else
        {
            if(y > z)
            {
                return y;
            }
            return z;
        }
    }
}
