package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLTextur;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.collider.GUCollider;
import com.gmail.pkr4mer.glooputil.object.collider.GUCylinderCollider;
import com.gmail.pkr4mer.glooputil.object.collider.GUSphereCollider;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;
import com.gmail.pkr4mer.util.ReflectionUtilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonas on 24.01.14.
 */
public class GUObject {     // This class contains all methods from GLObjekt so that GLObjekt isn't needed anymore. It also si able to interact with other glooputil classes.

    protected GLObjekt glo;
    protected Scene scene;

    protected String tag;
    protected String name;
    protected double[] defaultScales;

    protected GUCollider collider;

    private CaseInsensitiveMap<GUScript> scripts;

    protected boolean valid = true;

    public GUObject(GLObjekt g, Scene s, String t, String n)
    {
        glo = g;                                // The "(proto)type" of GUObject
        defaultScales = new double[]{1,1,1};    // Create Default Scale Array
        loadDefaultScales();
        scene = s;                              // The scene the GUObject is in
        tag = t;                                // The tag of the GUObject
        name = n;                               // The GUObject's name
        scripts = new CaseInsensitiveMap<>();    // The Scripts of the GUObject
    }

    public String getName() // Returns the GUObject's name
    {
        return name;
    }

    public boolean setName(String name)
    {
        if(name.equalsIgnoreCase(getName())) return true;
        if(scene.rename(getName(), name))
        {
            this.name = name;
            return true;
        }
        return false;
    }

    public ArrayList<String> getScripts()
    {
        return new ArrayList<>(scripts.keySet());
    }

    public String getTag()  // Returns the GUObject's tag
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public Scene getScene() // Returns the GUObject's scene
    {
        return scene;
    }

    public void setPosition(double pX, double pY, double pZ){   // Sets the GUObject's position to the given coordinates
        glo.setzePosition(pX, pY, pZ);
    }
    public void setPosition(Vector pPosition){  // Sets the GUObject's position to the given vector
        glo.setzePosition(pPosition.getX(), pPosition.getY(), pPosition.getZ());
    }
    public void rotate(double x, double y, double z) // Rotates the GUObject by the given arguments
    {
        glo.drehe(x,y,z);
    }

    public void rotate(Vector v)  // Rotates the GUObject by the given argumentss
    {
        rotate(v.getX(), v.getY(), v.getZ());
    }

    public boolean hasCollider()
    {
        return collider != null && collider.isValid();
    }

    public GUCollider getCollider()
    {
        return collider;
    }

    public void destroyCollider()
    {
        if(hasCollider())
        {
            collider.destroy();
            collider = null;
        }
    }

    public boolean addSphereCollider()
    {
        if(this instanceof GUEllipsoid)
        {
            collider = new GUSphereCollider(this,((GUEllipsoid)this).getBiggestRadius());
        }
        else if(this instanceof GUPrismoid)
        {
            collider = new GUSphereCollider(this,((GUPrismoid)this).getBiggestRadius());
        }
        else if(this instanceof GUCube)
        {
            collider = new GUSphereCollider(this,((GUCube)this).cornerDistance());
        }
        else
        {
            collider = new GUSphereCollider(this,1);
        }
        return false;
    }

    public boolean addCylinderCollider()
    {
        if(this instanceof GUEllipsoid)
        {
            collider = new GUCylinderCollider(this,((GUEllipsoid)this).getBiggestRadius(),((GUEllipsoid)this).getBiggestRadius());
        }
        else if(this instanceof GUPrismoid)
        {
            collider = new GUCylinderCollider(this,((GUPrismoid)this).getBiggestRadXZ(),this.getScaleY()*2);
        }
        else if(this instanceof GUCube)
        {
            collider = new GUCylinderCollider(this,((GUCube)this).getLongestDistanceXZ(),((GUCube)this).getHeight());
        }
        else
        {
            collider = new GUCylinderCollider(this,1,1);
        }
        return false;
    }

    @Deprecated
    public void turnAround(Vector rot, Vector pt){  // Turns the GUObject by the vector rot around the vector pt
        glo.drehe(rot.getX(), rot.getY(), rot.getZ(), pt.getX(), pt.getY(), pt.getZ());
    }

    @Deprecated
    public void turnAround(double pDx, double pDy, double pDz, Vector pt){  // Turns the GUObject by the given degrees around the vector pt
        glo.drehe(pDx, pDy, pDz, pt.getX(), pt.getX(), pt.getZ());
    }

    public Vector getPosition(){    // Returns the GUObject's Position as an vector
        return Vector.fromGLVector(glo.gibPosition());
    }

    public GLTextur getTexture(){   // Returns the GUObject's texture
        return glo.gibTextur();
    }

    public double getX(){   // Returns the GUObject's x-position
        return glo.gibX();
    }

    public double getY(){   // Returns the GUObject's y-position
        return glo.gibY();
    }

    public double getZ(){   // Returns the GUObject's z-position
        return glo.gibZ();
    }

    public boolean destroy(){   // Deletes the GUObject
        if(!valid) return false;
        glo.loesche();
        valid = false;
        destroyCollider();
        return scene.destroy(getName());
    }
    public void rotate(double pDegrees, Vector pDirection, Vector pLocation){   // Rotates the GUObject by the given degrees
        glo.rotiere(pDegrees, pDirection.toGLVektor(), pLocation.toGLVektor());
    }

    public void setRotation(double pDx, double pDy, double pDz)    // Sets the GUObject's rotation to the given degrees
    {
        glo.setzeDrehung(pDx,pDy,pDz);
    }
    
    public void setColor(double pR, double pG, double pB){  // Sets the GUObject's color to the given RGB-values
        glo.setzeFarbe(pR, pG, pB);
    }

    public void setScale(double pS){    // Sets the GUObject's scale to the given size
        glo.setzeSkalierung(pS * defaultScales[0], pS * defaultScales[1], pS * defaultScales[2]);
    }

    public void setScale(double pX, double pY, double pZ){  // Sets the GUObject's scale to the given arguments
        glo.setzeSkalierung(pX * defaultScales[0], pY * defaultScales[1], pZ * defaultScales[2]);
    }

    public void scale(double pX, double pY, double pZ)
    { // Scales the GUObject by the given arguments
        glo.skaliere(pX, pY, pZ);
    }

    public void move(double pX, double pY, double pZ){  // Moves the GUObject  by the given lengths in each direction
        glo.verschiebe(pX, pY, pZ);
    }

    public void move(Vector pVec){  // Moves the GUObject along the vector pVec
        glo.verschiebe(pVec.toGLVektor());
    }

    public void setTexture(String pFilename) // Sets the GUObject's texture to the given filename
    {
        glo.setzeTextur(pFilename);
    }

    public Vector getRotation() // Returns the GUObject's rotation
    {
        return new Vector(getRotX(),getRotY(),getRotZ());
    }

    public void setRotation(Vector v)    // Sets the GUObject's rotation to the given vector
    {
        glo.setzeDrehung(v.getX(),v.getY(),v.getZ());
    }

    public Vector getForward()  // Returns the GUObject's forward-pointing vector
    {
        Vector v = new Vector(getRotX(),getRotY(),getRotZ());
        v.multiply(1/v.magnitude());
        return v;
    }

    public void forward(double distance)    // Moves the GUObject forward by the given distance
    {
        Vector f = getForward();
        f.multiply(distance);
        move(f);
    }
    public void resetDisplayList(){ // Resets the display list
        glo.resetDisplayliste();
    }
    public void setQuality(int pQ){ // Sets the GUObject's quality to the given value
        glo.setzeQualitaet(pQ);
    }
    public void setVisible(boolean pV){ // Sets weather the GUObject is visible (pV == true) or not (pV == false)
        glo.setzeSichtbarkeit(pV);
    }

    public final void fixedUpdate()   // Activates the scripts
    {
        Map<Integer,List<GUScript>> ordered = new HashMap<>();
        for(GUScript gus : scripts.values())
        {
            if(!ordered.containsKey(gus.getRunPriority().getValue()))
            {
                ordered.put(gus.getRunPriority().getValue(),new ArrayList<GUScript>());
            }
            ordered.get(gus.getRunPriority().getValue()).add(gus);
        }
        for( int lvl : GUScript.RunPriority.getValues() )
        {
            if(ordered.containsKey(lvl))
            {
                for( GUScript gus : ordered.get(lvl) )
                {
                    gus.fixedUpdate();
                }
            }
        }
        if(collider != null) collider.fixedUpdate();
    }

    public boolean addScript(GUScript script)  // Adds a new script to the GUObject
    {
        if(scripts.containsKey(script.getTypeName())) return false;
        scripts.put(script.getTypeName(),script);
        script.setGUObject(this);
        return true;
    }

    public boolean hasScript(String typeName)
    {
        return scripts.containsKey(typeName);
    }

    public GUScript getScript(String typeName)
    {
        return scripts.get(typeName);
    }

    public void setTexture(GUTexture pTex){ // Sets the GUObject's Texture to the given GUTexture
        glo.setzeTextur(pTex);
    }

    public void setShininess(double pR, double pG, double pB, int pStrength){   // Sets the GUObject's shininess to the given RGB-color and strength
        glo.setzeGlanz(pR, pG, pB, pStrength);
    }

    public void setMaterial(float[][] pm){  // Sets the GUObject's material to the given value
        glo.setzeMaterial(pm);
    }

    public void setSelfIlluminating(double pR, double pG, double pB){   // Sets the GUObject's self illuminating to the given RGC-color
        glo.setzeSelbstleuchten(pR, pG, pB);
    }

    public double getScaleX()
    {
        Class<?> cls = glo.getClass();
        while( !cls.getName().equalsIgnoreCase("gloop.globjekt") )
        {
            cls = cls.getSuperclass();
        }
        try {
            Field field = cls.getDeclaredField("zScaleX");
            field.setAccessible(true);
            return (float) field.get(glo) / defaultScales[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public double getScaleY()
    {
        Class<?> cls = glo.getClass();
        while( !cls.getName().equalsIgnoreCase("gloop.globjekt") )
        {
            cls = cls.getSuperclass();
        }
        try {
            Field field = cls.getDeclaredField("zScaleY");
            field.setAccessible(true);
            return (float) field.get(glo) / defaultScales[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public double getScaleZ()
    {
        Class<?> cls = glo.getClass();
        while( !cls.getName().equalsIgnoreCase("gloop.globjekt") )
        {
            cls = cls.getSuperclass();
        }
        try {
            Field field = cls.getDeclaredField("zScaleZ");
            field.setAccessible(true);
            return (float) field.get(glo) / defaultScales[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private float[] getMatrix()
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

    public float getRotX()
    {
        return getMatrix()[8];
    }

    public float getRotY()
    {
        return getMatrix()[9];
    }

    public float getRotZ()
    {
        return getMatrix()[10];
    }

    public void debugMatrix()
    {
        int i = 0;
        for( float f : getMatrix() )
        {
            System.out.println(i + "=" + f);
            i++;
        }
    }

    private void loadDefaultScales()
    {
        this.defaultScales[0] = getScaleX();
        this.defaultScales[1] = getScaleY();
        this.defaultScales[2] = getScaleZ();
    }

    public double getDefaultScaleX()
    {
        return defaultScales[0];
    }

    public double getDefaultScaleY()
    {
        return defaultScales[1];
    }

    public double getDefaultScaleZ()
    {
        return defaultScales[2];
    }
}