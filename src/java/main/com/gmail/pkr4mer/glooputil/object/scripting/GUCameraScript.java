package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUCamera;

/**
 * Created by peter on 1/27/14.
 */
public abstract class GUCameraScript
{
    private GUCamera guCamera;

    public GUCameraScript() {}

    public final void setGUCamera(GUCamera o)
    {
        this.guCamera = o;
    }

    public abstract void fixedUpdate();

    public final GUCamera getGUObject()
    {
        return guCamera;
    }

    public abstract String getTypeName();
}