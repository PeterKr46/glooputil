package com.gmail.pkr4mer.glooputil.position;

import GLOOP.GLKamera;
import GLOOP.GLObjekt;
import GLOOP.GLVektor;
import com.gmail.pkr4mer.util.Pair;

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
        return add(v.x,v.y,v.z);
    }

    public Vector add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector addClone(Vector v)
    {
        return clone().add(v);
    }

    public Vector addClone(double x, double y, double z)
    {
        return clone().add(x,y,z);
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

    public Vector normalize()
    {
        setMagnitude(1);
        return this;
    }

    public Vector normalizeClone()
    {
        return clone().normalize();
    }

    public Vector flatten()
    {
        y = 0;
        return this;
    }

    @Override
    public Vector clone()
    {
        return new Vector(x,y,z);
    }

    public double magnitude()
    {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector setMagnitude(double magnitude)
    {
        double currerntMag = magnitude();
        x *= magnitude/currerntMag;
        z *= magnitude/currerntMag;
        y *= magnitude/currerntMag;
        return this;
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
        // →a * →b
        double top = a.multiplyClone(b).magnitude();
        // |a| * |b|
        double bottom = a.magnitude() * b.magnitude();
        if(bottom == 0.0) return 0;
        double total = top / bottom;
        return Math.toDegrees(Math.acos(total));
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

    public Vector round()
    {
        x = Math.floor(x);
        y = Math.floor(y);
        z = Math.floor(z);
        return this;
    }

    public Vector toAngles()
    {
        /*double x = -Math.toDegrees(Math.atan(this.y/this.z));
        double y = Math.toDegrees(Math.atan(this.z/this.x));
        double z = 0;//Math.toDegrees(Math.atan(this.x/this.y));
        if(this.y/this.z == Double.NaN) x = 0;
        if(this.z/this.x == Double.NaN) y = 0;*/
        double z = new Vector(this.x,this.y,0).getAngle(new Vector(0,1,0));
        double y = new Vector(this.x,0,this.z).getAngle(new Vector(1,0,0));
        double x = new Vector(0,this.y,this.z).getAngle(new Vector(0,0,1));//TODO Pythagoras
        return new Vector(x,y,z);
    }

    public static class Rotation
    {
        private static GLObjekt glo = new GLObjekt() {
            @Override
            protected void zeichneObjekt(GL2 gl2, GLU glu, GLKamera glKamera) {

            }
        };

        public static Vector getForward(Vector angles)
        {
            return getForward(angles.x,angles.y,angles.z);
        }

        /*
         * @return Pair<Position,Rotation>
         */
        public static Pair<Vector,Vector> simulateRotateAround(Vector prevRotation, Vector prevPosition, Vector hinge, Vector direction, float degrees)
        {
            //System.out.println(hinge + "<- Hinge Point");
            glo.setzePosition(prevPosition.toGLVektor());
            glo.setzeDrehung(prevRotation.getX(),prevRotation.getY(),prevRotation.getZ());
            //glo.rotiere(degrees,hinge.getX(),hinge.getY(),hinge.getZ(),direction.getX(),direction.getY(),direction.getZ());
            glo.rotiere(degrees,direction.toGLVektor(),hinge.toGLVektor());
            return new Pair<>(Vector.fromGLVector(glo.gibPosition()),getForward().toAngles());
        }

        public static Vector getForward(double rotX, double rotY, double rotZ)
        {
            glo.setzeDrehung(0, 0, 0);
            glo.drehe(rotX, rotY, rotZ);
            //debugMatrix();
            return getForward();
        }

        private static Vector getForward()
        {
            return new Vector(getRotX(),getRotY(),getRotZ());
        }

        public static void debugMatrix(GLObjekt glo)
        {
            int i = 0;
            for( float f : getMatrix(glo) )
            {
                System.out.println(i + "=" + f);
                i++;
            }
        }

        public static float[] getMatrix(GLObjekt glo)
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

        private static float getRotX()
        {
            return getMatrix(glo)[8];
        }

        private static float getRotY()
        {
            return getMatrix(glo)[9];
        }

        private static float getRotZ()
        {
            return getMatrix(glo)[10];
        }

        public static Vector getRotation(GLObjekt glo)
        {
            float[] matrix = getMatrix(glo);
            debugMatrix(glo);
            for(int i = 0; i < matrix.length; i++ )
            {
                for( int j = i; j < matrix.length; j++ )
                {
                    for( int k = i; k < matrix.length; k++ )
                    {
                        System.out.println(new Vector(matrix[i],matrix[j],matrix[k]).toAngles().round() + " | " + new Vector(i,j,k));
                    }
                }
            }
            return new Vector(matrix[8],matrix[9],matrix[10]);
        }
    }
}