package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLQuader;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUCube;
import com.gmail.pkr4mer.glooputil.object.GUPrismoid;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
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
                GUPrismoid cone = createCylinder(new double[]{5, 5, 5}, new double[]{10, 10, 10}, Axis.Y);
                System.out.println("cube '" + cone.getName() + "' " + cone);
                new GLLicht();
                cone.rotate(45);
                new GLSchwenkkamera().zeigeAchsen(true);
                cone.addScript(
                    new GUScript(cone)
                    {
                        @Override
                        public void fixedUpdate()
                        {
                            //getGuObject().forward(1);
                            System.out.println("My position: " + getGuObject().getPosition());
                        }
                    });
            }

            @Override
            public void update()
            {

            }
        };
    }
}
