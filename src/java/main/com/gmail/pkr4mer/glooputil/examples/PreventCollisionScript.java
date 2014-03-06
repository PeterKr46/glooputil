package com.gmail.pkr4mer.glooputil.examples;

import com.gmail.pkr4mer.glooputil.object.collider.Collider;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/20/14.
 */
public class PreventCollisionScript extends BehaviourScript
{
    @Override
    public void onStart()
    {
        getTransform().createCollider();
    }

    @Override
    public void fixedUpdate()
    {

    }

    @Override
    public void onCollisionStay(Collider c)
    {
        Vector direction = c.getAbsoluteCenter().difference(getTransform().getCollider().getAbsoluteCenter());
        direction.setMagnitude(0.01);
        while(c.collidesWith(getTransform().getCollider()))
        {
            getTransform().move(direction);
        }
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.HIGHEST;
    }
}
