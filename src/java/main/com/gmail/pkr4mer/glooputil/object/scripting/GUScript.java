package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUObject;

/**
 * Created by peter on 1/25/14.
 */
public abstract class GUScript
{
    private GUObject guObject;

    public GUScript() {}

    public final void setGUObject(GUObject o)
    {
        this.guObject = o;
    }

    public abstract void fixedUpdate();

    public final GUObject getGUObject()
    {
        return guObject;
    }

    public abstract String getTypeName();
}
