package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLLicht;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/26/14.
 */
public class GULight extends GUObject
{

    public GULight(GLLicht g, Scene s, String t, String n, Vector dir) {
        super(g, s, t, n, dir);
    }

    protected GLLicht getLight()
    {
        return (GLLicht) glo;
    }

    public void setColor(int r, int b, int g)
    {
        getLight().setzeFarbe(r,g,b);
    }

    public void setDimming(double d)
    {
        getLight().setzeAbschwaechung(d);
    }
}
