package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLObjekt;
import GLOOP.GLQuader;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.object.GUPrismoid;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.ReflectionUtilities;

import java.lang.reflect.Field;

public class Beispiele
{
    public static void main(String[] args)
    {
        new Scene()
        {
            @Override
            public void build()
            {
                new GLLicht();
                new GLSchwenkkamera().zeigeAchsen(true);
                GUObject cube = createCube(new double[]{0, 0, 0}, new double[]{1, 1, 1});
                cube.rotate(70,30,30);
                System.out.println(cube.getRotX());
                System.out.println(cube.getRotY());
                System.out.println(cube.getRotZ());
            }

            @Override
            public void update()
            {

            }
        };
    }
}
