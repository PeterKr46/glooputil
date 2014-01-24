package com.gmail.pkr4mer.glooputil;

import GLOOP.GLKamera;
import GLOOP.GLObjekt;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

import java.util.HashMap;

/**
 * Created by peter on 1/24/14.
 */
public abstract class Scene
{
    private CaseInsensitiveMap<GLObjekt> objekte;
    private boolean running;
    private GLKamera kamera;

    public Scene()
    {
        objekte = new CaseInsensitiveMap<>();
        running = true;
        erstelleKamera(0,0,0);
    }

    public GLObjekt sucheObject(String name)
    {
        return objekte.get(name);
    }

    public abstract void build();

    private void mainThread()
    {
        while(running)
        {
            GloopUtil.correctZ(getKamera());
        }
    }
      
    private GLKamera erstelleKamera(double x, double y, double z)
    {
        kamera = new GLKamera();
        kamera.setzePosition(x,y,z);
        return kamera;
    }

    public GLKamera getKamera()
    {
        return kamera;
    }
}
