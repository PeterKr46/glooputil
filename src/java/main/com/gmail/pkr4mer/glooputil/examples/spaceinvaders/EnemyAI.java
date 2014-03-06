package com.gmail.pkr4mer.glooputil.examples.spaceinvaders;

import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

/**
 * Created by peter on 3/6/14.
 */
public class EnemyAI extends BehaviourScript
{

    private double minX, maxX, speed;
    private int direction = 1;

    public EnemyAI(double pMinX, double pMaxX, double pSpeed)
    {
        minX = pMinX;
        maxX = pMaxX;
        speed = pSpeed;
    }

    @Override
    public void onStart()
    {
        minX += getTransform().getX();
        maxX += getTransform().getX();
    }

    public void move()
    {
        double curX = getTransform().getAbsolutePosition().getX();
        if(curX >= maxX)
        {
            direction = -1;
        }
        else if(curX <= minX)
        {
            direction = 1;
        }
        getTransform().move(speed*direction,0,0);
    }

    @Override
    public void fixedUpdate()
    {
        move();
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.LOW;
    }
}