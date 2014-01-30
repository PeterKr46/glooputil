package com.gmail.pkr4mer.glooputil.position;

import GLOOP.GLKamera;
import GLOOP.GLObjekt;
import GLOOP.GLVektor;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import java.lang.reflect.Field;


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

    public Vector addClone(Vector v)
    {
        return clone().add(v);
    }

    public Vector setX(double x)
    {
        this.x = x;
        return this;
    }

    public Vector setXClone(double x)
    {
        return clone().setX(x);
    }

    public Vector setY(double y)
    {
        this.y = y;
        return this;
    }

    public Vector setYClone(double y)
    {
        return clone().setY(y);
    }

    public Vector setZ(double z)
    {
        this.z = z;
        return this;
    }

    public Vector setZClone(double z)
    {
        return clone().setZ(z);
    }

    public Vector multiply(double mul)
    {
        this.x *= mul;
        this.y *= mul;
        this.z *= mul;
        return this;
    }

    public Vector multiplyClone(double mul)
    {
        return clone().multiply(mul);
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
        double sumA = a.magnitude();
        // |b|
        double sumB = b.magnitude();
        double total = top / (sumA * sumB);
        return Math.acos(Math.toRadians(total))*180/Math.PI;
    }

    public double[] toDoubleArray()
    {
        return new double[]{x,y,z};
    }

    public Vector differenceClone(Vector v)
    {
        return clone().difference(v);
    }

    public Vector difference(Vector v)
    {
        x = v.x - x;
        y = v.y - y;
        z = v.z - z;
        return this;
    }

    public Vector multiplyClone(Vector v)
    {
        return clone().multiply(v);
    }

    public Vector multiply(Vector v)
    {
        x = v.x * x;
        y = v.y * y;
        z = v.z * z;
        return this;
    }


    public static class Rotation
    {
        private static GLObjekt glo = new GLObjekt() {
            @Override
            protected void zeichneObjekt(GL2 gl2, GLU glu, GLKamera glKamera) {

            }
        };

        public static Vector getForward(Vector v)
        {
            return getForward(v.x,v.y,v.z);
        }

        public static Vector getForward(double rotX, double rotY, double rotZ)
        {
            glo.setzeDrehung(0, 0, 0);
            glo.drehe(rotX, rotY, rotZ);
            return new Vector(getRotX(),getRotY(),getRotZ());
        }

        private static float[] getMatrix()
        {
            Class<?> cls = glo.getClass();
            while( !cls.getName().equalsIgnoreCase("gloop.globjekt") )
            {
                cls = cls.getSuperclass();
            }
            try {
                Field field = cls.getDeclaredField("zMatrix");
                field.setAccessible(true);
                return (float[]) field.get(glo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static float getRotX()
        {
            return getMatrix()[8];
        }

        public static float getRotY()
        {
            return getMatrix()[9];
        }

        public static float getRotZ()
        {
            return getMatrix()[10];
        }
    }
}