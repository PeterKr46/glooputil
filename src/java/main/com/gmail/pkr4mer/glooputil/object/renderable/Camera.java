package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLKamera;
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

    public Camera(int width,int height,Vector position, Scene scene, String tag, String name)
    {
        super(position, scene, tag, name);
        backend = new GLKamera(width,height);
        backend.setzePosition(position.toGLVektor());
    }

    @Override
    public void createCollider() {

    }

    @Override
    protected boolean destroyBackend() {
        return false;
    }

    @Override
    public void rotate(Vector degr) {}

    @Override
    public void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees) { }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees) { }

    @Override
    public boolean addChild(ObjectHolder t)
    {
        throw new UnsupportedOperationException("ObjectHolders may not be attached to the Camera.");
    }

    public void lookAt(Transform pTransform)
    {
        if(pTransform != null) targetPosition = pTransform.getAbsolutePosition();
    }

    @Override
    protected void updateBackend()
    {
        if(backend == null) return;
        Vector pos = getAbsolutePosition();
        if(pos.getZ() == 0) pos.setZ(0.00001);
        else if(pos.getX() == 0) pos.setX(0.00001);
        backend.setzePosition(pos.toGLVektor());
        backend.setzeBlickpunkt(targetPosition.toGLVektor());
        backend.setzeScheitelrichtung(0,1,0);
    }
}
