package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLObjekt;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.collider.Collider;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/14/14.
 */
public abstract class VisibleTransform extends Transform
{
    protected GLObjekt backend;
    protected double[] color = new double[]{1,1,1};

    public VisibleTransform(Vector position, Scene scene, String tag, String name) throws Exception
    {
        super(position, scene, tag, name);
    }

    public VisibleTransform(Vector position, Scene scene, Transform parent, String tag, String name) throws Exception {
        super(position, scene, parent, tag, name);
    }

    public void setColor(double r, double g, double b)
    {
        backend.setzeFarbe(r,g,b);
        color[0] = r;
        color[1] = g;
        color[2] = b;
    }

    public abstract void createCollider();

    public double[] getColor()
    {
        return color.clone();
    }
}