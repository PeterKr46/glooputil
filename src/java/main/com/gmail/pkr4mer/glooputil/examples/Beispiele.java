package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLSchwenkkamera;
import GLOOP.GLVektor;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.scripting.*;
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
                createLight(new Vector(0, 35, 0)).setDimming(0.2);
                getCamera().setTargetPoint(new Vector(0, 0, 0));
                getCamera().setPosition(new Vector(40, 30, 40));

                //Tisch
                createCube(new Vector(0, 10, 0), new Vector(30, 12, 10)).setColor(0,1,0);
                createCylinder(new  Vector(0,0,0),new Vector(1,10,1),Axis.Y).setColor(0, 1, 0);
                createCylinder(new  Vector(29,0,9),new Vector(30,10,10),Axis.Y).setColor(0, 1, 0);
                createCylinder(new  Vector(29,0,0),new Vector(30,10,1),Axis.Y).setColor(0, 1, 0);
                createCylinder(new  Vector(0,0,9),new Vector(1,10,10),Axis.Y).setColor(0, 1, 0);

                //Raum
                createCube(new Vector(-50,-1,-50),new Vector(50,0,50)).setColor(0.6,0.3,0.1);
                createCube(new Vector(-50,0,-50),new Vector(-10,50,-45)).setColor(0.6,0.3,0.1);
                createCube(new Vector(-10,0,-50),new Vector(10,15,-45)).setColor(0.6,0.3,0.1);
                createCube(new Vector(10,0,-50),new Vector(50,50,-45)).setColor(0.6,0.3,0.1);
                createCube(new Vector(-50,40,-50),new Vector(50,50,-45)).setColor(0.6,0.3,0.1);
                createCube(new Vector(-10,13,-50),new Vector(10,15,-40)).setColor(0.6,0.3,0.1);

                //Stuhl
                createTruncatedCone(new Vector(5,0,-10),new Vector(15,5,0),0.1f,Axis.Y);

                GUCube cube = createCube(new Vector(0,10,30),new Vector(0.5,10.5,30.5));
            }

            @Override
            public void update()
            {

            }
        };
    }
}
