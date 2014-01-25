package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLTextur;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jonas on 24.01.14.
 */
public class GUObject {     // This class contains all methods from GLObjekt so that GLObjekt isn't needed anymore. It also si able to interact with other glooputil classes.

    protected GLObjekt glo;
    protected Scene scene;
    protected Vector rotation;

    protected String tag;
    protected String name;

    private List<GUScript> scripts;

    protected boolean valid = true;

    public GUObject(GLObjekt g, Scene s, String t, String n, Vector dir)
    {
        glo = g;                                // The "(proto)type" of GUObject
        scene = s;                              // The scene the GUObject is in
        rotation = dir;                         // The rotation of the GUObject
        tag = t;                                // The tag of the GUObject
        name = n;                               // The GUObject's name
        scripts = new ArrayList<GUScript>();    // The Scripts of the GUObject
    }

    public String getName() // Returns the GUObject's name
    {
        return name;
    }

    public String getTag()  // Returns the GUObject's tag
    {
        return tag;
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
    public void rotate(double pDx, double pDy, double pDz){ // Rotates the GUObject by the given arguments
        rotation.add(new Vector(pDx, pDy, pDz));
        fixRotation();
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

    public void delete(){   // Deletes the GUObject
        glo.loesche();
        valid = false; //TODO dieses Objekt muss auf Scene zugreifen und sich löschen
    }
    public void rotate(double pDegrees, Vector pDirection, Vector pLocation){   // Rotates the GUObject by the given degrees
        glo.rotiere(pDegrees, pDirection.toGLVektor(), pLocation.toGLVektor());
    }

    public void setRotation(double pDx, double pDy, double pDz){    // Sets the GUObject's rotation to the given degrees
        rotation = new Vector(pDx, pDy, pDz);
        fixRotation();
    }

    private void setGLRotation(Vector rotation) // Staff only :P
    {
        glo.setzeDrehung(rotation.getX(),rotation.getY(),rotation.getZ());
    }
    
    public void setColor(double pR, double pG, double pB){  // Sets the GUObject's color to the given RGB-values
        glo.setzeFarbe(pR, pG, pB);
    }

    public void setScale(double pS){    // Sets the GUObject's scale to the given size
        glo.setzeSkalierung(pS);
    }

    public void setScale(double pX, double pY, double pZ){  // Sets the GUObject's scale to the given arguments
        glo.setzeSkalierung(pX, pY, pZ);
    }

    public void scale(double pS){   // Scales the GUObject by the given size
        glo.skaliere(pS);
    }

    public void scale(double pX, double pY, double pZ){ // Scales the GUObject by the given arguments
        glo.skaliere(pX, pY, pZ);
    }

    public void move(double pX, double pY, double pZ){  // Moves the GUObject  by the given lengths in each direction
        glo.verschiebe(pX, pY, pZ);
    }

    public void move(Vector pVec){  // Moves the GUObject along the vector pVec
        glo.verschiebe(pVec.toGLVektor());
    }

    public void setTexture(String pFilename){   // Sets the GUObject's texture to the given filename
        glo.setzeTextur(pFilename);
    }

    public Vector getRotation() // Returns the GUObject's rotation
    {
        return rotation.clone();
    }

    public void setRotation(Vector rotation)    // Sets the GUObject's rotation to the given vector
    {
        glo.setzeDrehung(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Vector getForward()  // Returns the GUObject's forward-pointing vector
    {
        return new Vector(
                Math.tan(Math.toRadians(rotation.getX())),
                Math.tan(Math.toRadians(rotation.getZ())),
                Math.tan(Math.toRadians(rotation.getY()))
            );
    }

    public void forward(double distance)    // Moves the GUObject forward by the given distance
    {
        move(getForward().multiply(distance));
    }

    private void fixRotation()  // Fixes the rotation values
    {
        while( rotation.getX() > 360 )
        {
            rotation.setX(rotation.getX()-360);
        }
        while(rotation.getX() < 0 )
        {
            rotation.setX(rotation.getX()+360);
        }

        while( rotation.getY() > 360 )
        {
            rotation.setY(rotation.getY() - 360);
        }
        while(rotation.getY() < 0 )
        {
            rotation.setY(rotation.getY() + 360);
        }

        while( rotation.getZ() > 360 )
        {
            rotation.setZ(rotation.getZ() - 360);
        }
        while(rotation.getZ() < 0 )
        {
            rotation.setZ(rotation.getZ() + 360);
        }
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

    public void fixedUpdate()   // Activates every GUObject's script
    {
        setGLRotation(rotation);
        for(GUScript gus : scripts) gus.fixedUpdate();
    }

    public void addScript(GUScript script)  // Adds a new script to the GUObject
    {
        scripts.add(script);
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
}
