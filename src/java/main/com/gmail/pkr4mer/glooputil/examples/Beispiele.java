package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLObjekt;
import GLOOP.GLQuader;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.GUEllipsoid;
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
                createEllipsoid(new double[]{0, 0, 0}, new double[]{10, 15, 10}).addScript(
                        new GUScript() {
                            @Override
                            public void fixedUpdate() {
                                if (getGuObject().getScaleX() == 1.2) {
                                    getGuObject().setScale(1.0);
                                } else {
                                    getGuObject().setScale(1.2);
                                }
                            }
                        }
                );
                getCamera().setPosition(new Vector(50,50,50));
                getCamera().setShowAxes(true);
                new GLSchwenkkamera().zeigeAchsen(true);
                setRenderDistance(30);
            }

            @Override
            public void update()
            {
                GUEllipsoid cube = (GUEllipsoid) findObject("sphere");
                cube.forward(0.2);
                cube.rotate(0.36,0.36,0.36);
                createEllipsoid(new double[]{cube.getX() - 2, cube.getY() - 2, cube.getZ() - 2},new double[]{cube.getX() + 2, cube.getY() + 2, cube.getZ() + 2});
                //getCamera().setPosition(getCamera().getPosition().add(new Vector(1,1,1)));
            }
        };
    }
}
