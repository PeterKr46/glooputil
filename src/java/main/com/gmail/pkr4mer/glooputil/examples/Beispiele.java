package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLSchwenkkamera;
import GLOOP.GLVektor;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.GUEllipsoid;
import com.gmail.pkr4mer.glooputil.object.GUPrismoid;
import com.gmail.pkr4mer.glooputil.object.collider.GUCollider;
import com.gmail.pkr4mer.glooputil.object.scripting.ForceAttentionScript;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;

public class Beispiele
{
    public static void main(String[] args)
    {
        new Scene()
        {
            private GLSchwenkkamera k;
            @Override
            public void build()
            {
                createLight(new Vector(0, 10, 0)).setDimming(0.5);
                getCamera().setTargetPoint(new Vector(0, 0, 0));
                getCamera().setPosition(new Vector(40, 40, 40));
                createCube(new Vector(-50,-1,-50),new Vector(50,0,50));
                GUPrismoid dach = createPrismoid(new Vector(-10, 5, -5), new Vector(10, 10, 5), 4, 0, Axis.Y);
                dach.rotate(180,0,0);
                dach.setColor(255,0,0);
                GUCube koerper = createCube(new Vector(-6,0,-3),new Vector(6,5,3));
                GUCube turm = createCube(new Vector(5,0,-1), new Vector(7,10,1));
                k = new GLSchwenkkamera(360,360);
            }

            @Override
            public void update()
            {

            }
        };
    }
}
