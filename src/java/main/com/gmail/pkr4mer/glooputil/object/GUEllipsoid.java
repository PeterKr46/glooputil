package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLKugel;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/24/14.
 */
public class GUEllipsoid extends GUObject
{

    public GUEllipsoid(Scene scene, GLKugel sphere, Vector dir, String tag, String name) {
        super(sphere, scene, tag, name, dir);
    }

    protected GLKugel getSphere()
    {

        return (GLKugel) glo;
    }

    public void setQuality(int q)
    {
        getSphere().setzeQualitaet(q);
    }
}
