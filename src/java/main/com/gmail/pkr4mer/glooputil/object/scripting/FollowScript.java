package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUObject;

/**
 * Created by peter on 1/26/14.
 */
public class FollowScript extends TargetedScript
{

    public FollowScript(GUObject target) {
        super(target);
    }

    @Override
    public void fixedUpdate()
    {
        if(getTarget() != null) getGUObject().setPosition(getTarget().getPosition());
    }

    @Override
    public String getTypeName()
    {
        return "FollowScript";
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.LOW;
    }
}
