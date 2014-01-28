package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUEllipsoid;
import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.object.GUPrismoid;
import com.gmail.pkr4mer.glooputil.object.collider.GUCollider;
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
                getCamera().setPosition(20, 10, 50);
                getCamera().setTargetPoint(new Vector(20,10,20));
                getCamera().setShowAxes(true);
                GUPrismoid e = createCylinder(new double[]{0, 0,0 }, new double[]{20, 20, 20},Axis.Y);
                e.addCylinderCollider();
                GUEllipsoid e2 = createEllipsoid(new double[]{25, 5, 5}, new double[]{35, 15, 15});
                e2.addSphereCollider();
                e.addScript(
                        new GUScript() {
                            @Override
                            public void fixedUpdate() {
                            }

                            @Override
                            public String getTypeName() {
                                return "CustomScript-1";
                            }

                            @Override
                            public RunPriority getRunPriority() {
                                return RunPriority.HIGHEST;
                            }

                            @Override
                            public void onCollisionEnter(GUCollider c) {
                                System.out.println(c.getGUObject().getName() + " collided with " + getGUObject().getName());
                            }
                        }
                );
                e2.addScript(
                        new GUScript() {
                            @Override
                            public void fixedUpdate() {
                                getGUObject().move(-0.2, 0, 0);
                            }

                            @Override
                            public String getTypeName() {
                                return "CustomScript-2";
                            }

                            @Override
                            public RunPriority getRunPriority() {
                                return RunPriority.HIGHEST;
                            }

                            @Override
                            public void onCollisionEnter(GUCollider c) {
                                System.out.println(c.getGUObject().getName() + " collided with " + getGUObject().getName());
                            }
                        }
                );
                createLight(new Vector(0, 15, 0));
                //new GLSchwenkkamera(360,360).zeigeAchsen(true);
            }

            @Override
            public void update()
            {
            }
        };
    }
}
