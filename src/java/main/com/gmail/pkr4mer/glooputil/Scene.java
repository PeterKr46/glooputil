package com.gmail.pkr4mer.glooputil;

import GLOOP.*;
import com.gmail.pkr4mer.glooputil.object.*;
import com.gmail.pkr4mer.glooputil.object.collider.GUCollider;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 1/24/14.
 */
public class Scene implements ObjectHolder
{
    private CaseInsensitiveMap<Transform> objects;
    private boolean running;
    private GLTastatur keyboard;

    public Scene()
    {
        objects = new CaseInsensitiveMap<>();
        running = true;
        try {
            mainThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Transform findObject(String name)
    {
        return objects.get(name);
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
                            for( Transform o : new ArrayList<>(objects.values()) )
                            {
                                o.fixedUpdate();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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
        Transform guo = objects.remove(name);
        objects.put(newName,guo);
        return guo.setName(name);
    }

    public boolean destroy(String name)
    {
        if(!isNameTaken(name)) return false;
        Transform guo = objects.remove(name);
        return guo.destroy();
    }

    public void destroy()
    {
        for( Transform guo : objects.values() )
        {
            guo.destroy();
        }
    }

    @Override
    public boolean addChild(ObjectHolder child)
    {
        throw new UnsupportedOperationException("A Scene Object's Children cannot be directly manipulated.");
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
        return new ArrayList<ObjectHolder>(objects.values());
    }

    @Override
    public boolean removeChild(ObjectHolder child)
    {
        throw new UnsupportedOperationException("A Scene Object's Children cannot be directly manipulated.");
    }

    public void register(Transform transform) throws Exception
    {
        if(this.isNameTaken(transform.getName())) throw new Exception("Name '" + transform.getName() + "' already taken.");
        objects.put(transform.getName(),transform);
    }

    public List<GUCollider> getColliders()
    {
        List<GUCollider> result = new ArrayList<>();
        for(Transform t : objects.values() )
        {
            result.addAll(t.getAllColliders());
        }
        return result;
    }
}