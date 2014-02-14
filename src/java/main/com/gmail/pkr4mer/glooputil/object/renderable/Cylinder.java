package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLPrismoid;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.ObjectHolder;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/5/14.
 */
public class Cylinder extends VisibleTransform
{
    public Cylinder(Vector positionA, Vector positionB, double radius, Scene scene, String tag, String name) throws Exception
    {
        super(positionA.addClone(positionA.differenceClone(positionB).multiply(0.5)), scene, tag, name);
        Vector center = positionA.addClone(positionA.differenceClone(positionB).multiply(0.5));
        if( this.name == null || scene.isNameTaken(this.name) )
        {
            this.name = scene.getAvailableName("cylinder");
        }
        backend = new GLPrismoid(center.toGLVektor(),radius,radius,(int)Math.floor(2 * Math.PI * radius)+5,positionA.distance(positionB));
        Vector diff = positionA.difference(positionB);
        setForward(diff);
        double y = Axis.X.toVector().getAngle(new Vector(getForward().getX(),0,getForward().getZ()));
        Vector vX = Axis.Z.toVector();
        vX.multiply(Math.sqrt(diff.getX()*diff.getX() + diff.getZ()*diff.getZ()));
        double x = vX.getAngle(new Vector(0,getForward().getY(),getForward().getZ()));
        backend.drehe(-x,y,0);
    }

    public Cylinder(Vector positionA, Vector positionB, double radius, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        super(positionA.addClone(positionA.differenceClone(positionB).multiply(0.5)), scene, parent, tag, name);
        Vector center = positionA.addClone(positionA.differenceClone(positionB).multiply(0.5));
        if( this.name == null || scene.isNameTaken(this.name) )
        {
            this.name = scene.getAvailableName("cylinder");
        }
        backend = new GLPrismoid(center.toGLVektor(),radius,radius,(int)Math.floor(2 * Math.PI * radius)+5,positionA.distance(positionB));
        Vector diff = positionA.difference(positionB);
        setForward(diff);
        double y = Axis.X.toVector().getAngle(new Vector(getForward().getX(),0,getForward().getZ()));
        Vector vX = Axis.Z.toVector();
        vX.multiply(Math.sqrt(diff.getX()*diff.getX() + diff.getZ()*diff.getZ()));
        double x = vX.getAngle(new Vector(0,getForward().getY(),getForward().getZ()));
        backend.drehe(-x,y,0);
    }

    public Cylinder(Vector position, double radius, double height, Scene scene, String tag, String name) throws Exception
    {
        super(position, scene, tag, name);
        if( this.name == null || scene.isNameTaken(this.name) )
        {
            this.name = scene.getAvailableName("cylinder");
        }
        backend = new GLPrismoid(position.toGLVektor(),radius, radius, (int)Math.floor(2 * Math.PI * radius), height);
    }

    public Cylinder(Vector position, double radius, double height, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        super(position, scene, parent, tag, name);
        if( this.name == null || scene.isNameTaken(this.name) )
        {
            this.name = scene.getAvailableName("cylinder");
        }
        backend = new GLPrismoid(position.toGLVektor(),radius, radius, (int)Math.floor(2 * Math.PI * radius), height);
    }

    @Override
    public void rotate(Vector degr)
    {
        backend.drehe(degr.getX(),degr.getY(),degr.getZ());
        for(ObjectHolder t : getChildren())
        {
            if( t instanceof Transform)
            {
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(1,0,0),(float)degr.getX());
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(0,1,0),(float)degr.getY());
                ((Transform) t).rotateAround(getAbsolutePosition(),new Vector(0,0,1),(float)degr.getZ());
            }
        }
    }

    @Override
    public void rotateAround(double posX, double posY, double posZ, double axisX, double axisY, double axisZ, float degrees)
    {
        backend.rotiere(degrees,axisX,axisY,axisZ,posX,posY,posZ);
    }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees)
    {
        rotateAround(position.getX(),position.getY(),position.getZ(),direction.getX(),direction.getY(),direction.getZ(),degrees);
    }

    @Override
    protected void updateBackend()
    {
        backend.setzePosition(getAbsolutePosition().toGLVektor());
    }
}
