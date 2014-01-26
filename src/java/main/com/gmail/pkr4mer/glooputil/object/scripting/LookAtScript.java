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
        //TODO Skalarberechnung mit http://www.mp.haw-hamburg.de/pers/Vassilevskaya/download/m1/vektoren/skalar/richtung-1.pdf, Seite 9
        Vector d = getGUObject().getPosition().difference(getTarget().getPosition());
        double angleZ = new Vector(d.getX(),d.getY(),0).getAngle(new Vector(0,0,1));
        double angleY = new Vector(d.getX(),0,d.getZ()).getAngle(new Vector(1,0,0));
        double angleX = new Vector(0,d.getY(),d.getZ()).getAngle(new Vector(1,0,0));
        Vector requiredRotation = new Vector(angleX,angleY,angleZ);
        System.out.println(d + " | " + requiredRotation);
        getGUObject().setRotation(requiredRotation);
    }

    @Override
    public String getTypeName() {
        return "LookAtScript";
    }
}
