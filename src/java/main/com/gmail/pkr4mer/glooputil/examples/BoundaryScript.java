package com.gmail.pkr4mer.glooputil.examples;

import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

/**
 * Created by peter on 2/20/14.
 */
public class BoundaryScript extends BehaviourScript
{

    private double minX, maxX;
    private double minZ, maxZ;

    public BoundaryScript(double pXMin, double pXMax, double pZmin, double pZmax)
    {
        minX = pXMin;
        minZ = pZmin;
        maxX = pXMax;
        maxZ = pZmax;
    }

    @Override
    public void fixedUpdate()
    {
        if(getTransform().getX() > maxX) getTransform().setPosition(maxX,getTransform().getY(),getTransform().getZ());
        if(getTransform().getX() < minX) getTransform().setPosition(minZ,getTransform().getY(),getTransform().getZ());
        if(getTransform().getZ() > maxZ) getTransform().setPosition(getTransform().getX(),getTransform().getY(),maxZ);
        if(getTransform().getZ() < minZ) getTransform().setPosition(getTransform().getX(),getTransform().getY(),minZ);
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.HIGHEST;
    }
}
