package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLPrismoid;
import com.gmail.pkr4mer.glooputil.Scene;

/**
 * Created by peter on 1/24/14.
 */
public class GUPrismoid extends GUObject
{

    public GUPrismoid(Scene scene, GLPrismoid g, String t, String n) {
        super(scene, g, t, n);
    }

    protected GLPrismoid getPrismoid()
    {
        return (GLPrismoid) GLO;
    }
}
