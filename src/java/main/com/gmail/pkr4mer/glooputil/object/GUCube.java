package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLQuader;
import com.gmail.pkr4mer.glooputil.Scene;

/**
 * Created by peter on 1/24/14.
 */
public class GUCube extends GUObject
{

    public GUCube(Scene scene, GLQuader g, String t, String n) {
        super(scene, g, t, n);
    }

    protected GLQuader getCube()
    {
        return (GLQuader) GLO;
    }
}
