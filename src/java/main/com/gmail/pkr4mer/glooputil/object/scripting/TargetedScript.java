package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUObject;

/**
 * Created by peter on 1/27/14.
 */
public abstract class TargetedScript extends GUScript
{
    private GUObject target;

    public TargetedScript(GUObject target)
    {
        this.target = target;
    }

    public GUObject getTarget()
    {
        return target;
    }

    public void setTarget(GUObject guo)
    {
        this.target = guo;
    }
}
