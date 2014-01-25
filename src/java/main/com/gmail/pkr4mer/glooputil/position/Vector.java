package com.gmail.pkr4mer.glooputil.position;

import GLOOP.GLVektor;
import com.jogamp.graph.math.Quaternion;

/**
 * Created by peter on 1/24/14.
 */
public class Vector
{
    public static Vector zero()
    {
        return new Vector(0,0,0);
    }
    public static Vector forward()
    {
        return new Vector(0,0,1);
    }

    public static Vector fromGLVector(GLVektor glv)
    {
        return new Vector(glv.x, glv.y, glv.z);
    }

    private double x,y,z;

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public Vector add(Vector v)
    {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public Vector multiply(double mul)
    {
        this.x *= mul;
        this.y *= mul;
        this.z *= mul;
        return this;
    }

    public GLVektor toGLVektor()
    {
        return new GLVektor(x,y,z);
    }

    public Vector clone()
    {
        return new Vector(x,y,z);
    }

    public double magnitude()
    {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public String toString()
    {
        return "Vector(" + x + "," + y + "," + z + ")";
    }

    public static Vector fromQuaternion(Quaternion q)
    {
        return new Vector(q.getX(),q.getY(),q.getZ());
    }

    public static Quaternion create(float x1, float y1, float z1)
    {
        float a = (float)Math.PI*0.5f;
        // Here we calculate the sin( theta / 2) once for optimization
        float result = (float) Math.sin( a / 2.0f );

        // Calculate the x, y and z of the quaternion
        x1 *= result;
        y1 *= result;
        z1 *= result;

        // Calcualte the w value by cos( theta / 2 )
        float w = (float) Math.cos( a / 2.0f );

        Quaternion q = new Quaternion(x1,y1,z1,w);
        //q.normalize();
        return q;
    }
}