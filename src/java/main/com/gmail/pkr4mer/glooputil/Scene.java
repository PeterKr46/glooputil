package com.gmail.pkr4mer.glooputil;

import GLOOP.*;
import com.gmail.pkr4mer.glooputil.object.*;
import com.gmail.pkr4mer.glooputil.object.collider.GUCollider;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 1/24/14.
 */
public abstract class Scene
{
    private CaseInsensitiveMap<GUObject> objects;
    private boolean running;
    private GUCamera camera;
    private GLTastatur keyboard;

    public Scene()
    {
        objects = new CaseInsensitiveMap<>();
        running = true;
        createCamera(0, 0, 0);
        keyboard = new GLTastatur();
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

    private void renderDistanceThread() throws Exception
    {
        Field worldF = Class.forName("GLOOP.GLGlobal").getDeclaredField("WELT");
        worldF.setAccessible(true);
        Object worldO = worldF.get(null);
        Field threadF = worldO.getClass().getDeclaredField("syncUpdate");
        threadF.setAccessible(true);
        final Object threadO = threadF.get(worldO);
        new Thread()
        {
            public void run()
            {
                while(true)
                {
                    try {
                        synchronized (threadO)
                        {
                            updateRenderDistance();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    private void updateRenderDistance()
    {
        /* Temporarily disabled
        for( GUObject o : new ArrayList<>(objects.values()) )
        {
            if( getCamera().getPosition().distance(o.getPosition()) > renderDistance )
            {
                o.setVisible(false);
            }
            else
            {
                o.setVisible(true);
            }
        } */
    }
      
    private GUCamera createCamera(double x, double y, double z)
    {
        System.out.println("Created Camera at " + new Vector(x,y,z));
        camera = new GUCamera(new GLKamera(),this);
        camera.setPosition(x, y, z);
        camera.setTargetPoint(new Vector(0,0,0));
        return camera;
    }

    public GUCamera getCamera()
    {
        return camera;
    }

    public GULight createLight(Vector pos)
    {
        GLLicht light = new GLLicht(pos.getX(),pos.getY(),pos.getZ());
        GULight l = new GULight(light,this,null,getAvailableName("light"));
        objects.put(l.getName(),l);
        return l;
    }

    public GUPrismoid createPrismoid(Vector min, Vector max, int verts, float rad2, Axis direction)
    {
        double[][] edges = Util.sortiere(min.toDoubleArray(), max.toDoubleArray());
        double[] edge1 = edges[0];
        double[] edge2 = edges[1];
        GLPrismoid prismoid = new GLPrismoid(0,0,0,1,rad2,verts,1);
        GUPrismoid prism;
        if( direction == Axis.Y )
        {
            prismoid.drehe(270,0,0);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(width*0.5,depth*0.5,height);
        }
        else if( direction == Axis.Z )
        {
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(width*0.5,height*0.5,depth);
        }
        else
        {
            prismoid.drehe(0,90,0);
            double height = edge2[1] - edge1[1];
            double width = edge2[0] - edge1[0];
            double depth = edge2[2] - edge1[2];
            double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
            prism = new GUPrismoid(this,prismoid,null,getAvailableName("prism"));
            prism.setPosition(center[0],center[1],center[2]);
            prism.scale(depth*0.5,height*0.5,width);
        }
        objects.put(prism.getName(),prism);
        return prism;
    }

    public GUPrismoid createCone(Vector min, Vector max, Axis direction)
    {
        return createTruncatedCone(min, max, 0, direction);
    }

    public GUPrismoid createTruncatedCone(Vector min, Vector max, float rad2, Axis direction)
    {
        return createPrismoid(min, max, 100, rad2, direction);
    }

    public GUPrismoid createCylinder(Vector min, Vector max, Axis direction)
    {
        return createPrismoid(min, max, 100, direction);
    }

    public GUPrismoid createPrismoid(Vector min, Vector max, int verts, Axis direction)
    {
        return createPrismoid(min,max,verts,1.0f,direction);
    }

    public GUCube createCube(Vector min, Vector max)
    {
        double[][] edges = Util.sortiere(min.toDoubleArray(), max.toDoubleArray());
        double[] edge1 = edges[0];
        double[] edge2 = edges[1];
        double width = edge2[0] - edge1[0];                        // Calc width
        double height = edge2[1] - edge1[1];                       // Calc height
        double depth = edge2[2] - edge1[2];                        // Calc length
        double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
        GLQuader cube = new GLQuader(center[0],center[1],center[2], 1, 1, 1);
        cube.setzeSkalierung(width,height,depth);
        GUCube cb = new GUCube(this,cube,null,getAvailableName("cube"));
        objects.put(cb.getName(),cb);
        return cb;
    }

    public GUEllipsoid createEllipsoid(Vector min, Vector max)
    {
        double[][] edges = Util.sortiere(min.toDoubleArray(), max.toDoubleArray());
        double[] ecke1 = edges[0];
        double[] ecke2 = edges[1];
        double width = ecke2[0] - ecke1[0];
        double height = ecke2[1] - ecke1[1];
        double depth = ecke2[2] - ecke1[2];
        double[] center = new double[]{ecke1[0] + width * 0.5, ecke1[1] + height * 0.5, ecke1[2] + depth * 0.5};
        double radius = 0.5;
        GLKugel sphere = new GLKugel(center[0],center[1],center[2],0.5);
        double skalX = (width * 0.5)/radius;
        double skalY = (height * 0.5)/radius;
        double skalZ = (depth * 0.5)/radius;
        sphere.setzeSkalierung(skalX,skalY,skalZ);
        GUEllipsoid ellipsoid = new GUEllipsoid(this,sphere,null,getAvailableName("sphere"));
        objects.put(ellipsoid.getName(),ellipsoid);
        return ellipsoid;
    }

    public String getAvailableName(String name)
    {
        if(!isNameTaken(name)) return name;
        int i = 0;
        do {
            i++;
        } while(isNameTaken(name + "_" + i));
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

    public boolean isKeyPressed(String key)
    {
        if(key.length() == 1)
        {
            return keyboard.istGedrueckt(key.charAt(0));
        }
        if(key.equalsIgnoreCase("ctrl")) return keyboard.strg();
        if(key.equalsIgnoreCase("alt")) return keyboard.alt();
        if(key.equalsIgnoreCase("enter")) return keyboard.enter();
        if(key.equalsIgnoreCase("esc")) return keyboard.esc();
        if(key.equalsIgnoreCase("del")) return keyboard.backspace();
        if(key.equalsIgnoreCase("shift")) return keyboard.shift();
        if(key.equalsIgnoreCase("up")) return keyboard.oben();
        if(key.equalsIgnoreCase("down")) return keyboard.unten();
        if(key.equalsIgnoreCase("left")) return keyboard.rechts();
        if(key.equalsIgnoreCase("right")) return keyboard.links();
        return key.equalsIgnoreCase("tab") && keyboard.tab();
    }

    public boolean rename(String name, String newName)
    {
        if(!isNameTaken(name) || isNameTaken(newName)) return false;
        GUObject guo = objects.remove(name);
        objects.put(newName,guo);
        return guo.setName(name);
    }

    public List<GUCollider> getColliders()
    {
        List<GUCollider> gucs = new ArrayList<>();
        for(GUObject guo : objects.values())
        {
            if(guo.hasCollider())gucs.add(guo.getCollider());
        }
        return gucs;
    }

    public boolean destroy(String name)
    {
        if(!isNameTaken(name)) return false;
        GUObject guo = objects.remove(name);
        return guo.destroy();
    }

    public void destroy()
    {
        for( GUObject guo : objects.values() )
        {
            guo.destroy();
        }
    }
}