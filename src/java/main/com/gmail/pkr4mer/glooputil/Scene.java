package com.gmail.pkr4mer.glooputil;

import GLOOP.GLKamera;
import GLOOP.GLObjekt;
import GLOOP.GLPrismoid;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

/**
 * Created by peter on 1/24/14.
 */
public abstract class Scene
{
    private CaseInsensitiveMap<GLObjekt> objects;
    private boolean running;
    private GLKamera camera;

    public Scene()
    {
        objects = new CaseInsensitiveMap<>();
        running = true;
        createCamera(0, 0, 0);
    }

    public GLObjekt findObject(String name)
    {
        return objects.get(name);
    }

    public abstract void build();

    public abstract void update();

    private void mainThread()
    {
        while(running)
        {
            GloopUtil.correctZ(getCamera());
        }
    }
      
    private GLKamera createCamera(double x, double y, double z)
    {
        camera = new GLKamera();
        camera.setzePosition(x, y, z);
        return camera;
    }

    public GLKamera getCamera()
    {
        return camera;
    }

    public GLPrismoid erstellePrismoid(double[] edge1, double[] edge2, int seiten, float rad2, Axis direction)
    {
        double[][] edges = GloopUtil.sortiere(edge1, edge2);
        edge1 = edges[0];
        edge2 = edges[1];
        GLPrismoid prismoid = new GLPrismoid(0,0,0,1,rad2,seiten,1);
        if( direction == Axis.Y )
        {
            prismoid.drehe(270,0,0);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prismoid.setzePosition(center[0],center[1],center[2]);
            prismoid.skaliere(width*0.5,depth*0.5,height);
            return prismoid;
        }
        else if( direction == Axis.Z )
        {
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prismoid.setzePosition(center[0],center[1],center[2]);
            prismoid.skaliere(width*0.5,height*0.5,depth);
        }
        else if( direction == Axis.X )
        {
            prismoid.drehe(0,90,0);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prismoid.setzePosition(center[0],center[1],center[2]);
            prismoid.skaliere(depth*0.5,height*0.5,width);
        }
        return prismoid;
    }

    public GLPrismoid erstelleKegel(double[] edge1, double[] edge2, Axis direction)
    {
        return createConeStump(edge1, edge2, 0, direction);
    }

    public GLPrismoid createConeStump(double[] edge1, double[] edge2, float rad2, Axis direction)
    {
        return erstellePrismoid(edge1,edge2,100,rad2,direction);
    }
}