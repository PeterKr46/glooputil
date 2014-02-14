package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLKamera;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.ObjectHolder;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/14/14.
 */
public class Camera extends Transform {

    private GLKamera backend;
    private Vector targetPosition = Vector.zero();

    public Camera(int width,int height,Vector position, Scene scene, String tag, String name) throws Exception
    {
        super(position, scene, tag, name);
        backend = new GLSchwenkkamera(width,height);
    }

    @Override
    public void rotate(Vector degr) { }

    @Override
    public void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees) { }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees) { }

    @Override
    public boolean addChild(ObjectHolder t)
    {
        throw new UnsupportedOperationException("ObjectHolders may not be attached to the Camera.");
    }

    @Override
    protected void updateBackend()
    {
        if(backend == null) return;
        backend.setzePosition(
                getAbsolutePosition().
                        toGLVektor()
        );
        backend.setzeBlickpunkt(targetPosition.toGLVektor());
    }
}
