package com.gmail.pkr4mer.glooputil;

import GLOOP.*;
import com.gmail.pkr4mer.glooputil.object.*;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 1/24/14.
 */
public class Scene implements ObjectHolder, ScriptHolder
{
    private CaseInsensitiveMap<Transform> objects;
    private boolean running;
    private GLTastatur keyboard;

    public Scene()
    {
        objects = new CaseInsensitiveMap<>();
        running = true;
        keyboard = new GLTastatur();
        try {
            mainThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                while(true)
                {
                    try {
                        Thread.sleep(50L);
                        synchronized (threadO)
                        {
                            runScripts();
                            for( Transform o : new ArrayList<>(objects.values()) )
                            {
                                o.runScripts();
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

    public boolean addChild(Transform child)
    {
        return false;
    }

    @Override
    public boolean addChild(ObjectHolder child) {
        return false;
    }

    @Override
    public Transform getParent() {
        return null;
    }

    @Override
    public boolean setParent(ObjectHolder parent) {
        return false;
    }

    @Override
    public boolean isParentOf(ObjectHolder other) {
        return false;
    }

    @Override
    public boolean isChildOf(ObjectHolder other) {
        return false;
    }

    @Override
    public List<ObjectHolder> getChildren() {
        return new ArrayList<ObjectHolder>(objects.values());
    }

    @Override
    public void runScripts() {

    }

    @Override
    public GUScript getScript(String id) {
        return null;
    }

    @Override
    public boolean addScript(GUScript script) {
        return false;
    }

    @Override
    public boolean removeScript(GUScript script) {
        return false;
    }

    @Override
    public List<String> getScripts() {
        return null;
    }
}