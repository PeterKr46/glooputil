package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLPrismoid;
import com.gmail.pkr4mer.glooputil.Scene;

/**
 * Created by peter on 1/24/14.
 */
public class GUPrismoid extends Transform
{

    public GUPrismoid(Scene scene, GLPrismoid g, String t, String n) {
        super(g, scene, t, n);
    }

    protected GLPrismoid getPrismoid()
    {
        return (GLPrismoid) glo;
    }

    public double getBiggestRadXZ()
    {
        if(getScaleX() > getScaleZ()) return getScaleX()/2;
        return getScaleZ()/2;
    }

    public double getBiggestRadius()
    {
        double x = getScaleX()/2;
        double y = getScaleX()/2;
        double z = getScaleX()/2;
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
