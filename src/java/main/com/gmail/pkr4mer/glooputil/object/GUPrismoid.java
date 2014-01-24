package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLPrismoid;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/24/14.
 */
public class GUPrismoid extends GUObject
{

    public GUPrismoid(Scene scene, GLPrismoid g, Vector dir, String t, String n) {
        super(g, scene, t, n, dir);
    }

    protected GLPrismoid getPrismoid()
    {
        return (GLPrismoid) glo;
    }
}
