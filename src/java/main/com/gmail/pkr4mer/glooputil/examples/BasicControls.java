package com.gmail.pkr4mer.glooputil.examples;

import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/14/14.
 */
public class BasicControls extends BehaviourScript
{
    private double axisX = 0;
    private double axisZ = 0;
    public double speed = 10;

    public double minX = -256;
    public double maxX = 256;
    public double minZ = -256;
    public double maxZ = 256;

    @Override
    public String getTypeName()
    {
        return "BasicControls";
    }

    @Override
    public void onStart() {
        getTransform().getScene().registerKeyboard();
    }

    private void updateAxes()
    {
        boolean up = getTransform().getScene().isKeyPressed("up");
        boolean down = getTransform().getScene().isKeyPressed("down");
        boolean left = getTransform().getScene().isKeyPressed("left");
        boolean right = getTransform().getScene().isKeyPressed("right");
        if(left || right)
        {
            if(right) axisX += 0.1;
            else axisX -= 0.1;
        }
        else
        {
            axisX *= 0.8;
        }
        if(axisX < -1) axisX = -1;
        if(axisX > 1) axisX = 1;

        if(up || down)
        {
            if(up) axisZ += 0.1;
            else axisZ -= 0.1;
        }
        else
        {
            axisZ *= 0.8;
        }
        if(axisZ < -1) axisZ = -1;
        if(axisZ > 1) axisZ = 1;
    }

    @Override
    public void fixedUpdate()
    {
        updateAxes();
        Vector velocity = new Vector(axisX*-speed,0,axisZ*-speed);
        getTransform().move(velocity);
        if(getTransform().getX() > maxX) getTransform().setPosition(maxX,getTransform().getY(),getTransform().getZ());
        if(getTransform().getX() < minX) getTransform().setPosition(minZ,getTransform().getY(),getTransform().getZ());
        if(getTransform().getZ() > maxZ) getTransform().setPosition(getTransform().getX(),getTransform().getY(),maxZ);
        if(getTransform().getZ() < minZ) getTransform().setPosition(getTransform().getX(),getTransform().getY(),minZ);
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.MEDIUM;
    }
}
