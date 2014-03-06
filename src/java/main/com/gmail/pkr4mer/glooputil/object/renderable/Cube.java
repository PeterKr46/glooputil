package com.gmail.pkr4mer.glooputil.object.renderable;

import GLOOP.GLQuader;
import GLOOP.GLWuerfel;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.Util;
import com.gmail.pkr4mer.glooputil.object.ObjectHolder;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 2/5/14.
 */
public class Cube extends VisibleTransform
{

    public Cube(Vector position, double radius, Scene scene, String tag, String name)
    {
        super(position, scene, tag, name);
        backend = new GLWuerfel(position.toGLVektor(),radius);
    }

    public Cube(Vector min, Vector max, Scene scene, String tag, String name)
    {
        super(min.addClone(min.differenceClone(max).multiply(0.5)), scene, tag, name);
        double[][] edges = Util.sort(min.toDoubleArray(), max.toDoubleArray());
        double[] edge1 = edges[0];
        double[] edge2 = edges[1];
        double width = edge2[0] - edge1[0];                        // Calc width
        double height = edge2[1] - edge1[1];                       // Calc height
        double depth = edge2[2] - edge1[2];                        // Calc length
        double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
        backend = new GLQuader(center[0],center[1],center[2], 1, 1, 1);
        backend.setzeSkalierung(width, height, depth);
        defaultScale[0] = width;
        defaultScale[1] = height;
        defaultScale[2] = depth;
    }

    public Cube(Vector min, Vector max, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        super(min.addClone(min.differenceClone(max).multiply(0.5)), scene, parent, tag, name);
        double[][] edges = Util.sort(min.toDoubleArray(), max.toDoubleArray());
        double[] edge1 = edges[0];
        double[] edge2 = edges[1];
        double width = edge2[0] - edge1[0];                        // Calc width
        double height = edge2[1] - edge1[1];                       // Calc height
        double depth = edge2[2] - edge1[2];                        // Calc length
        double[] center = new double[]{edge1[0] + width * 0.5, edge1[1] + height * 0.5, edge1[2] + depth * 0.5};
        backend = new GLQuader(center[0],center[1],center[2], 1, 1, 1);
        backend.setzeSkalierung(width, height, depth);
        defaultScale[0] = width;
        defaultScale[1] = height;
        defaultScale[2] = depth;
    }

    public Cube(Vector position, double radius, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        super(position, scene, parent, tag, name);
        backend = new GLWuerfel(position.toGLVektor(),radius);
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

    @Override
    public void createCollider() {

    }

    @Override
    protected boolean destroyBackend() {
        backend.loesche();
        return true;
    }
}
