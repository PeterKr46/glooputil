package com.gmail.pkr4mer.glooputil.examples.spaceinvaders;

import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

/**
 * Created by peter on 3/6/14.
 */
public class PlayerControl extends BehaviourScript
{
    @Override
    public void fixedUpdate()
    {
        if(getTransform().getScene().isKeyPressed("left"))
        {
            getTransform().move(1,0,0);
        }
        else if(getTransform().getScene().isKeyPressed("right"))
        {
            getTransform().move(-1,0,0);
        }
    }

    @Override
    public RunPriority getRunPriority()
    {
        return RunPriority.LOW;
    }
}
