package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import com.gmail.pkr4mer.glooputil.Scene;
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
                GUPrismoid cone = createCylinder(new double[]{10, 10, 10}, new double[]{20, 20, 15}, Axis.X);
                System.out.println("cone '" + cone.getName() + "' " + cone);
                new GLLicht().setzeAbschwaechung(1);
                cone.forward(1);
                getCamera().setPosition(new Vector(50, 50, 50));
                cone.addScript(
                    new GUScript(cone)
                    {
                        @Override
                        public void fixedUpdate()
                        {
                            getGuObject().rotate(0,10,0);
                            getGuObject().move(Vector.forward());
                        }
                    });
            }

            @Override
            public void update()
            {
                GUPrismoid prism = (GUPrismoid) findObject("prism");
                //System.out.println("Dir: " + prism.getForward());
                //System.out.println("Deg: " + prism.getRotation());
                getCamera().setTargetPoint(prism.getPosition());
                getCamera().setShowAxes(true);
            }
        };
    }
}
