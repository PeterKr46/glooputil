package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLLicht;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 3/4/14.
 */
public class Light extends VisibleTransform
{
    private GLLicht licht;
    private double[] backgroundLight = new double[3];

    public Light(Vector position, Scene scene, String tag, String name) throws Exception
    {
        super(position, scene, tag, name);
        licht = new GLLicht();
    }

    public void setBackgroundLight(double r, double g, double b)
    {
        backgroundLight = new double[]{256D/r,256D/g,256D/b};
    }

    public void setColor(double r, double g, double b)
    {
        color = new double[]{256D/r,256D/g,256D/b};
    }

    @Override
    public void createCollider()
    {
        throw new UnsupportedOperationException("Lights cannot have colliders.");
    }

    @Override
    protected boolean destroyBackend() {
        backend.loesche();
        return true;
    }

    @Override
    public void rotate(Vector degr)
    {

    }

    @Override
    public void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees) {

    }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees) {

    }

    @Override
    protected void updateBackend()
    {
        licht.setzeFarbe(color[0],color[1],color[2]);
        licht.setzeHintergrundlicht(backgroundLight[0], backgroundLight[1], backgroundLight[2]);
    }
}