package com.gmail.pkr4mer.glooputil.examples;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/18/14.
 */
public class EvadeScript extends BehaviourScript
{
    private double maxSpeed;
    private double targetDistance;
    private Transform evade;
    public EvadeScript(Transform pEvade, double pMaxSpeed, double pTargetDistance)
    {
        evade = pEvade;
        maxSpeed = pMaxSpeed;
        targetDistance = pTargetDistance;
    }

    @Override
    public void fixedUpdate()
    {
        Vector evadeDirection = getTransform().getAbsolutePosition().difference(evade.getAbsolutePosition());
        evadeDirection.setY(0);
        double currentDistance = getTransform().getAbsolutePosition().distance(evade.getAbsolutePosition());
        if(currentDistance < targetDistance)
        {
            evadeDirection.setMagnitude((1-(currentDistance/targetDistance))*-maxSpeed);
            getTransform().move(evadeDirection);
        }
    }

    @Override
    public RunPriority getRunPriority()
    {
        return RunPriority.LOW;
    }
}
