package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLKugel;
import com.gmail.pkr4mer.glooputil.Scene;

/**
 * Created by peter on 1/24/14.
 */
public class GUEllipsoid extends GUObject
{

    public GUEllipsoid(Scene scene, GLKugel sphere, String tag, String name) {
        super(scene, sphere, tag, name);
    }

    protected GLKugel getSphere()
    {
        return (GLKugel) GLO;
    }

    public void setQuality(int q)
    {
        getSphere().setzeQualitaet(q);
    }
}
