package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.*;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.renderable.*;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

import java.io.File;

public class Examples
{
    private static Sphere balloon;
    public static void main(String[] args)
    {
        Scene scene = new Scene();
        new GLLicht(300,300,300).setzeHintergrundlicht(0,0,0);
        try {
            balloon = new Sphere(new Vector(0,100,0),50,scene,null,"Balloon");
            balloon.setColor(1,0,0);
            new Cube(new Vector(-15,0,-15),new Vector(13,15,-13),scene,balloon,null,"Wall1").setColor(0.5,0.4,0.2);
            new Cube(new Vector(-15,0,-13),new Vector(-13,15,15),scene,balloon,null,"Wall2").setColor(0.5,0.4,0.2);
            new Cube(new Vector(15,0,15),new Vector(-13,15,13),scene,balloon,null,"Wall3").setColor(0.5,0.4,0.2);
            new Cube(new Vector(15,0,13),new Vector(13,15,-15),scene,balloon,null,"Wall4").setColor(0.5,0.4,0.2);
            new Cube(new Vector(-15,-1,-15),new Vector(15,0,15),scene,balloon,null,"Floor").setColor(0.4,0.3,0.2);
            new Cylinder(new Vector(-14,14+25,-14),1,50,scene,balloon,null,"Rope1").rotate(90, 0, 0);
            new Cylinder(new Vector(-14,14+25,14),1,50,scene,balloon,null,"Rope2").rotate(90, 0, 0);
            new Cylinder(new Vector(14,14+25,-14),1,50,scene,balloon,null,"Rope3").rotate(90, 0, 0);
            new Cylinder(new Vector(14,14+25,14),1,50,scene,balloon,null,"Rope4").rotate(90,0,0);

            for(int i = 0; i < 30; i++)
            {
                Sphere s = new Sphere(new Vector(Math.random()*512-256,60,Math.random()*512-256),8,scene,null,null);
                s.addScript(new EvadeScript(balloon, 30, 200));
                s.addScript(new BoundaryScript(-256,256,-256,256));
                s.addScript(new PreventCollisionScript());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        scene.getCamera().setPosition(0, 800, 500);
        balloon.move(0,100,0);
        /*try {
            new Camera(500,500,balloon.getAbsolutePosition().add(new Vector(100, 30, 0)),scene,null,"Camera");//.setParent(balloon);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        new GLTerrain(0,0,0,new File("").getAbsolutePath() + "/resources/textures/lava.png");
        balloon.addScript(
                new BehaviourScript() {
                    private double i = 0;
                    private double timeScale = 0.1;
                    private double baseY;
                    private double magnitude = 5;

                    public void onStart() {
                        baseY = getTransform().getY();
                    }

                    @Override
                    public void fixedUpdate() {
                        i += timeScale;
                        double y = Math.sin(i) * magnitude;
                        getTransform().setPosition(getTransform().getAbsolutePosition().setY(baseY + y));
                    }

                    @Override
                    public RunPriority getRunPriority() {
                        return RunPriority.LOW;
                    }
                }
        );
        balloon.addScript(new BasicControls());
    }
}
