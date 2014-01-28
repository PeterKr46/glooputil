package com.gmail.pkr4mer.glooputil.object.scripting;

/**
 * Created by peter on 1/27/14.
 */
public class CircleScript extends GUScript
{
    private double rotationSpeed;
    private double movementSpeed;

    public CircleScript(double rot, double mov)
    {
        rotationSpeed = rot;
        movementSpeed = mov;
    }

    @Override
    public void fixedUpdate()
    {
        if(!getGUObject().getScene().isKeyPressed("w"))
        {
            getGUObject().forward(movementSpeed);
            getGUObject().rotate(0,rotationSpeed,0);
        }
    }

    @Override
    public String getTypeName() {
        return "CircleScript";
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.LOW;
    }
}
