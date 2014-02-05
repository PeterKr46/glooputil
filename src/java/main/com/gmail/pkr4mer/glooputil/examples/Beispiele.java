package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLSchwenkkamera;
import GLOOP.GLVektor;
import GLOOP.GLWuerfel;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.Cube;
import com.gmail.pkr4mer.glooputil.object.Sphere;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;

public class Beispiele
{
    public static void main(String[] args)
    {
        System.out.println(new Vector(0,1,0).getAngle(new Vector(1,0,1)));
        System.out.println(new Vector(7,20,56).toAngles());
        Scene scene = new Scene();
        final GLWuerfel testObj = new GLWuerfel(new GLVektor(20,20,0),5);
        new GLLicht();
        try {
            Cube a = new Cube(new Vector(-20,-20,0),10,scene,null,"sphere");
            a.addScript(
                    new GUScript() {
                        @Override
                        public void fixedUpdate() {
                            //getGUObject().rotateAround(getGUObject().getAbsolutePosition(),new Vector(1,0,0),1);
                            //getGUObject().move(getGUObject().getForward());
                            getGUObject().rotateAround(new Vector(0,0,0), new Vector(1,10,5),10);
                            testObj.rotiere(10,new GLVektor(1,10,5),new GLVektor(0,0,0));
                            if(Math.random() > 0.95) System.out.println(getGUObject().getRotation());
                        }

                        @Override
                        public RunPriority getRunPriority() {
                            return RunPriority.HIGHEST;
                        }
                    }
            );
            /*new Cube(new Vector(20,20,0),5,scene,a,null,"sphere2").addScript(
                    new GUScript() {
                        @Override
                        public void fixedUpdate() {
                            //System.out.println("Position> " + getGUObject().getAbsolutePosition());
                            //System.out.println("Rotation> " + getGUObject().getRotation());
                        }

                        @Override
                        public RunPriority getRunPriority() {
                            return RunPriority.LOW;
                        }
                    }
            );*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GLSchwenkkamera().zeigeAchsen(true);
    }
}
