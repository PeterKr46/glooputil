package com.gmail.pkr4mer.glooputil;

import GLOOP.*;
import com.gmail.pkr4mer.glooputil.object.*;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

/**
 * Created by peter on 1/24/14.
 */
public abstract class Scene
{
    private CaseInsensitiveMap<GUObject> objects;
    private boolean running;
    private GUCamera camera;

    public Scene()
    {
        objects = new CaseInsensitiveMap<>();
        running = true;
        createCamera(20, 20, 20);
        build();
        try {
            mainThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GUObject findObject(String name)
    {
        return objects.get(name);
    }

    public abstract void build();

    public abstract void update();

    private void mainThread() throws Exception
    {
        while(running)
        {
            Thread.sleep(50L);
            update();
            for( GUObject o : objects.values() )
            {
                o.fixedUpdate();
            }
        }
    }
      
    private GUCamera createCamera(double x, double y, double z)
    {
        //camera = new GUCamera(new GLKamera(),this);
        //camera.setPosition(x, y, z);
        //camera.setTargetPoint(new Vector(0,0,0));
        return camera;
    }

    public GUCamera getCamera()
    {
        return camera;
    }

    public GUPrismoid createPrismoid(double[] edge1, double[] edge2, int verts, float rad2, Axis direction)
    {
        double[][] edges = Util.sortiere(edge1, edge2);
        edge1 = edges[0];
        edge2 = edges[1];
        GLPrismoid prismoid = new GLPrismoid(0,0,0,1,rad2,verts,1);
        Vector rotation = new Vector(0,0,0);
        GUPrismoid prism;
        if( direction == Axis.Y )
        {
            prismoid.drehe(270,0,0);
            rotation.setX(270);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,rotation,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(width*0.5,depth*0.5,height);
        }
        else if( direction == Axis.Z )
        {
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,rotation,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(width*0.5,height*0.5,depth);
        }
        else
        {
            prismoid.drehe(0,90,0);
            rotation.setY(90);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,rotation,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(depth*0.5,height*0.5,width);
        }
        objects.put(prism.getName(),prism);
        return prism;
    }

    public GUPrismoid createCone(double[] edge1, double[] edge2, Axis direction)
    {
        return createTruncatedCone(edge1, edge2, 0, direction);
    }

    public GUPrismoid createTruncatedCone(double[] edge1, double[] edge2, float rad2, Axis direction)
    {
        return createPrismoid(edge1, edge2, 100, rad2, direction);
    }

    public GUPrismoid createCylinder(double[] edge1, double[] edge2, Axis direction)
    {
        return createPrismoid(edge1, edge2, 100, direction);
    }

    public GUPrismoid createPrismoid(double[] edge1, double[] edge2, int verts, Axis direction)
    {
        return createPrismoid(edge1,edge2,verts,1.0f,direction);
    }

    public GUCube createCube(double[] edge1, double[] edge2)
    {
        double[][] edges = Util.sortiere(edge1, edge2);                   // Sortiere in kleine und große edge
        edge1 = edges[0];
        edge2 = edges[1];
        double width = edge2[0] - edge1[0];                        // Berechne die width
        double height = edge2[1] - edge1[1];                         // Berechne die Höhe
        double depth = edge2[2] - edge1[2];                        // Berechne die Länge
        double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
        GLQuader cube = new GLQuader(center[0],center[1],center[2], width, height, depth);
        GUCube cb = new GUCube(this,cube,new Vector(0,0,0),null,getAvailableName("cube"));
        objects.put(cb.getName(),cb);
        System.out.println(cb + cb.getName());
        return cb;
    }

    public GUEllipsoid createEllipsoid(double[] ecke1, double[] ecke2)
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
        GUEllipsoid ellipsoid = new GUEllipsoid(this,sphere,new Vector(0,0,0),null,getAvailableName("sphere"));
        objects.put(ellipsoid.getName(),ellipsoid);
        return ellipsoid;
    }

    public String getAvailableName(String name)
    {
        if(!isNameTaken(name)) return name;
        int i = 0;
        do {
            i++;
        } while(isNameTaken(name + "_i"));
        return name + "_" + i;
    }

    public boolean isNameTaken(String name)
    {
        return objects.get(name) != null;
    }

    protected void debug()
    {
        for( String key : objects.keySet() )
        {
            System.out.println(key + " = " + objects.get(key));
        }
    }
}