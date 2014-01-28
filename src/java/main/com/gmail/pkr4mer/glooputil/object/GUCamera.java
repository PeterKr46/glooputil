package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLKamera;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.scripting.GUCameraScript;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

/**
 * Created by peter on 1/24/14.
 */
public class GUCamera
{
    private GLKamera glo;
    private Scene scene;
    private CaseInsensitiveMap<GUCameraScript> scripts;

    public GUCamera(GLKamera k, Scene s)
    {
        scene = s;
        glo = k;
    }

    public boolean addScript(GUCameraScript script)  // Adds a new script to the GUObject
    {
        if(scripts.containsKey(script.getTypeName())) return false;
        scripts.put(script.getTypeName(),script);
        script.setGUCamera(this);
        return true;
    }

    public boolean hasScript(String typeName)
    {
        return scripts.containsKey(typeName);
    }

    public GUCameraScript getScript(String typeName)
    {
        return scripts.get(typeName);
    }

    public void setTargetPoint(Vector vector)
    {
        glo.setzeBlickpunkt(vector.toGLVektor());
    }

    public void setShowAxes(boolean show)
    {
        glo.zeigeAchsen(show);
    }

    public Scene getScene()
    {
        return scene;
    }

    public void setPosition(double px, double py, double pz)
    {
        glo.setzePosition(px, py, pz);
    }

    public void setPosition(Vector pPosition){
        glo.setzePosition(pPosition.getX(), pPosition.getY(), pPosition.getZ());
    }
    public Vector getPosition(){
        return new Vector (glo.gibX(), glo.gibY(), glo.gibZ());
    }

    public double getX(){
        return glo.gibX();
    }
    public double getY(){
        return glo.gibY();
    }
    public double getZ(){
        return glo.gibZ();
    }
    public void rotate(double pDegrees, double pNX, double pNY, double pNZ, double pRX, double pRY, double pRZ){
        glo.rotiere(pDegrees, pNX, pNY, pNZ, pRX, pRY, pRZ);
    }
    public void rotate(double pDegrees, Vector pDirection, Vector pLocation){
        glo.rotiere(pDegrees, pDirection.toGLVektor(), pLocation.toGLVektor());
    }
    public void move(double pX, double pY, double pZ){
        glo.verschiebe(pX, pY, pZ);
    }
    public void move(Vector pVec){
        glo.verschiebe(pVec.toGLVektor());
    }

}
