package com.gmail.pkr4mer.glooputil;

import GLOOP.GLKamera;
import GLOOP.GLObjekt;

import java.util.HashMap;

/**
 * Created by peter on 1/24/14.
 */
public abstract class Scene
{
    private HashMap<String, GLObjekt> objekte;
    private boolean running;

    public Scene()
    {
        objekte = new HashMap<>();
        running = true;
    }

    private void mainThread()
    {
        while(running)
        {

        }
    }

    public GLKamera erstelleKamera()
    {
        erstelleKamera(0, 0, 0);
      
    public GLKamera erstelleKamera(double x, double y, double z)
    {
        GLKamera k = new GLKamera();
        k.setzePosition(x,y,z);
        return k;
    }
}
