package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.object.scripting.LookAtScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;

public class Beispiele
{
    public static void main(String[] args)
    {
        new Scene()
        {
            @Override
            public void build()
            {
                GUObject objA = createCone(new double[]{0,0,0},new double[]{10,10,10}, Axis.X);
                objA.setName("a");
                objA.setPosition(0,0,0);
                GUObject objB = createEllipsoid(new double[]{50, 60, 32}, new double[]{60, 70, 42});
                objB.setName("b");
                System.out.println(objA.addScript(new LookAtScript(objB)));
                getCamera().setPosition(5, 100, 5);
                getCamera().setShowAxes(true);
                new GLSchwenkkamera().zeigeAchsen(true);
                createLight(new Vector(0,15,0));
            }

            @Override
            public void update()
            {

            }
        };
    }
}
