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

    public double distance(Vector v)
    {
        double a,b,c;
        a = x - v.x;
        b = y - v.y;
        c = z - v.z;
        if( a < 0 ) a *= -1;
        if( b < 0 ) b *= -1;
        if( c < 0 ) c *= -1;
        return Math.sqrt(a*a + b*b + c*c);
    }

    public double distanceSquared(Vector v)
    {
        double a,b,c;
        a = x - v.x;
        b = y - v.y;
        c = z - v.z;
        if( a < 0 ) a *= -1;
        if( b < 0 ) b *= -1;
        if( c < 0 ) c *= -1;
        return a*a + b*b + c*c;
    }

    public double getAngle(Vector other)
    {
        return getAngle(this,other);
    }

    public static double getAngle(Vector a, Vector b)
    {
        // a * b
        double top = (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
        // |a|
        double sumA = Math.sqrt(a.x*a.x + a.y*a.y + a.z*a.z);
        // |b|
        double sumB = Math.sqrt(b.x*b.x + b.y*b.y + b.z*b.z);
        double total = top / (sumA * sumB);
        return Math.acos(Math.toRadians(total))*180/Math.PI;
    }

    public Vector difference(Vector v)
    {
        return new Vector(v.x - x, v.y - y, v.z - z);
    }

    public Vector extractX()
    {
        return new Vector(x,0,0);
    }

    public Vector extractY()
    {
        return new Vector(0,y,0);
    }

    public Vector extractZ()
    {
        return new Vector(0,0,z);
    }
}