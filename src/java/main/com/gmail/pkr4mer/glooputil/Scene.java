package com.gmail.pkr4mer.glooputil;

import GLOOP.GLTastatur;
import com.gmail.pkr4mer.glooputil.object.ObjectHolder;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.collider.Collider;
import com.gmail.pkr4mer.glooputil.object.renderable.Camera;
import com.gmail.pkr4mer.glooputil.position.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 1/24/14.
 */
public final class Scene implements ObjectHolder
{
    private List<Transform> children;
    private boolean running;
    private GLTastatur keyboard;
    private Camera camera;

    public Scene()
    {
        children = new ArrayList<>();
        running = true;
        try {
            camera = new Camera(640,640, new Vector(0,500,0),this,"MainCamera","MainCamera");
            mainThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Camera getCamera()
    {
        return camera;
    }

    public boolean stop()
    {
        if(!running) return false;
        running = false;
        return true;
    }

    public boolean registerKeyboard()
    {
        if(keyboard != null) return false;
        keyboard = new GLTastatur();
        return true;
    }

    public List<ObjectHolder> findObject(String name)
    {
        ArrayList<ObjectHolder> results = new ArrayList<>();
        for(Transform t : children)
        {
            results.addAll(t.findObject(name));
        }
        return results;
    }

    public void log(Object o)
    {
        System.out.println("[GU-Scene] " + o.toString());
    }

    private void mainThread() throws Exception
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
                while(running)
                {
                    try {
                        Thread.sleep(50L);
                        synchronized (threadO)
                        {
                            for( ObjectHolder o : getChildren() )
                            {
                                if(o instanceof Transform)
                                {
                                    ((Transform) o).fixedUpdate();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public String debug()
    {
        String result = "Scene\n";
        for(ObjectHolder o : getChildren())
        {
            result += o.debug(" - ") + "\n";
        }
        return result;
    }

    public String debug(String prefix)
    {
        return debug() + "\n";
    }

    public boolean isKeyPressed(String key)
    {
        if(keyboard == null) registerKeyboard();
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

    public void destroy()
    {
        for( Transform guo : children )
        {
            guo.destroy();
        }
    }

    @Override
    public boolean addChild(ObjectHolder child)
    {
        child.setParent(this);
        children.add((Transform) child);
        return true;
    }

    @Override
    public Transform getParent()
    {
        throw new UnsupportedOperationException("A Scene Object is always the root Object.");
    }

    @Override
    public boolean setParent(ObjectHolder parent)
    {
        throw new UnsupportedOperationException("A Scene Object is always the root Object.");
    }

    @Override
    public boolean isParentOf(ObjectHolder other)
    {
        return other.isChildOf(this);
    }

    @Override
    public boolean isChildOf(ObjectHolder other)
    {
        return false;
    }

    @Override
    public List<ObjectHolder> getChildren()
    {
        return new ArrayList<ObjectHolder>(children);
    }

    @Override
    public boolean removeChild(ObjectHolder child)
    {
        child.setParent(null);
        return children.remove(child);
    }

    public void register(Transform transform)
    {
        addChild(transform);
    }

    public List<Collider> getColliders()
    {
        List<Collider> result = new ArrayList<>();
        for(Transform t : children )
        {
            result.addAll(t.getAllColliders());
        }
        return result;
    }
}