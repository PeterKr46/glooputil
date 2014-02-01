package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLSchwenkkamera;
import GLOOP.GLZylinder;
import com.gmail.pkr4mer.glooputil.position.Vector;

public class Beispiele
{
    public static void main(String[] args)
    {
        Vector rot = new Vector(2,0,1);
        rot.multiply(1/rot.magnitude());
        System.out.println("Base: " + rot);
        rot = rot.toAngles();
        System.out.println("Angles: " + rot);
        GLZylinder z = new GLZylinder(0,0,0,5,15);
        z.drehe(rot.getX(), rot.getY(), rot.getZ());
        rot = Vector.Rotation.getForward(rot);
        System.out.println("Reconverted: " + rot);
        GLSchwenkkamera k = new GLSchwenkkamera(300,300);
        k.zeigeAchsen(true);
        k.setzePosition(20,0,20);
        rot.multiply(0.1);
        while(true)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            z.verschiebe(rot.toGLVektor());
        }
    }
}
