package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.GUPrismoid;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;

public class Beispiele
{
    private static long lastSwitch = 0l;
    private static int visible = 0;

    public static void main(String[] args)
    {
        new Scene()
        {
            @Override
            public void build()
            {
                GUPrismoid cone = createCone(new double[]{-2, -2, 10}, new double[]{2, 2, 15}, Axis.X);
                System.out.println("cone '" + cone.getName() + "' " + cone);
                new GLLicht();
                cone.forward(1);
            }

            @Override
            public void update()
            {
                getCamera().setPosition(new Vector(5,10,5));
                GUPrismoid prism = (GUPrismoid) findObject("prism");
                getCamera().setTargetPoint(new Vector(0,0,0));
                prism.forward(0.1);
                prism.rotate(0,2,0);
                System.out.println("Dir: " + prism.getForward());
                System.out.println("Deg: " + prism.getRotation());
                getCamera().setShowAxes(true);
            }
        };
    }
}
