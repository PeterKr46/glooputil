package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/27/14.
 */
public class LookAtScript extends TargetedScript
{

    public LookAtScript(GUObject target) {
        super(target);
    }

    @Override
    public void fixedUpdate()
    {
        Vector d = getGUObject().getPosition().difference(getTarget().getPosition());
        double y = Math.atan(Math.toRadians(d.getX()*d.getZ()))*180/Math.PI;
        double z = Math.atan(Math.toRadians(Math.sqrt(d.getX()*d.getX()*d.getZ()*d.getZ())*d.getY()))*180/Math.PI;
        double x = Math.atan(Math.toRadians(d.getY()*d.getZ()))*180/Math.PI;
        Vector requiredRotation = new Vector(x,y,z);
        System.out.println(d + " | " + requiredRotation);
        getGUObject().setRotation(requiredRotation);
    }

    @Override
    public String getTypeName() {
        return "LookAtScript";
    }

    @Override
    public RunPriority getRunPriority()
    {
        return RunPriority.HIGHEST;
    }
}
