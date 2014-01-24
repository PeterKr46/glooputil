package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLQuader;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/24/14.
 */
public class GUCube extends GUObject
{

    public GUCube(Scene scene, GLQuader g, Vector dir, String t, String n) {
        super(g, scene, t, n, dir);
    }

    protected GLQuader getCube()
    {
        return (GLQuader) glo;
    }
}
