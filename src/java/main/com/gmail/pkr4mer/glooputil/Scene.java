package com.gmail.pkr4mer.glooputil;

import GLOOP.*;
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
            update();
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

    public GLPrismoid createPrismoid(double[] edge1, double[] edge2, int seiten, float rad2, Axis direction)
    {
        double[][] edges = Util.sortiere(edge1, edge2);
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

    public GLPrismoid createCone(double[] edge1, double[] edge2, Axis direction)
    {
        return createTruncatedCone(edge1, edge2, 0, direction);
    }

    public GLPrismoid createTruncatedCone(double[] edge1, double[] edge2, float rad2, Axis direction)
    {
        return createPrismoid(edge1, edge2, 100, rad2, direction);
    }

    public GLPrismoid createCylinder(double[] edge1, double[] edge2, Axis direction)
    {
        return createPrismoid(edge1, edge2, 100, direction);
    }

    public GLPrismoid createPrismoid(double[] edge1, double[] edge2, int seiten, Axis direction)
    {
        return createPrismoid(edge1,edge2,seiten,1.0f,direction);
    }

    public static GLQuader createCube(double[] edge1, double[] edge2)
    {
        double[][] edges = Util.sortiere(edge1, edge2);                   // Sortiere in kleine und große edge
        edge1 = edges[0];
        edge2 = edges[1];
        double width = edge2[0] - edge1[0];                        // Berechne die width
        double height = edge2[1] - edge1[1];                         // Berechne die Höhe
        double depth = edge2[2] - edge1[2];                        // Berechne die Länge
        double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
        return new GLQuader(center[0],center[1],center[2], width, height, depth);
    }

    public static GLKugel createEllipsoid(double[] ecke1, double[] ecke2)
    {
        double[][] edges = Util.sortiere(ecke1, ecke2);
        ecke1 = edges[0];
        ecke2 = edges[1];
        double width = ecke2[0] - ecke1[0];
        double height = ecke2[1] - ecke1[1];
        double depth = ecke2[2] - ecke1[2];
        double[] center = new double[]{ecke1[0] + width * 0.5, ecke1[1] + height * 0.5, ecke1[2] + depth * 0.5};
        double diameter = width;
        if( height < width ) diameter = height;
        if( depth < diameter ) diameter = depth;
        double radius = diameter * 0.5;
        GLKugel sphere = new GLKugel(center[0],center[1],center[2],radius);
        double skalX = (width * 0.5)/radius;
        double skalY = (height * 0.5)/radius;
        double skalZ = (depth * 0.5)/radius;
        sphere.setzeSkalierung(skalX,skalY,skalZ);
        return sphere;
    }
}