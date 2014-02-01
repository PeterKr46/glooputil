package com.gmail.pkr4mer.glooputil.object;

import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/30/14.
 */
public interface Rotatable
{
    public Vector getForward();
    public void rotate(double x, double y, double z);

    public void rotate(Vector degr);

    public void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees);

    public void rotateAround(Vector position, Vector direction, float degrees);
}
