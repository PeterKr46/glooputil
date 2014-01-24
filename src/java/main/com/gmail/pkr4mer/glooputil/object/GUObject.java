package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLTextur;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;


/**
 * Created by Anwender on 24.01.14.
 */
public class GUObject {

    protected GLObjekt glo;
    protected Scene scene;

    protected String tag;
    protected String name;

    protected boolean valid = true;

    public GUObject(GLObjekt g, Scene s, String t, String n) {
        glo = g;
        scene = s;
        tag = t;
        name = n;
    }

    public void setPosition(double px, double py, double pz){
        glo.setzePosition(px, py, pz);
    }
    public void setPosition(Vector pPosition){
        glo.setzePosition(pPosition.getX(), pPosition.getY(), pPosition.getZ());
    }
    public void turn(double pWx, double pWy, double pWz){
        glo.drehe(pWx, pWy, pWz);
    }
    public void turn(double pWx, double pWy, double pWz, double px, double py, double pz){
        glo.drehe(pWx, pWy, pWz, px, py, pz);
    }
    public void turn(double pWx, double pWy, double pWz, Vector pPunkt){
        glo.drehe(pWx, pWy, pWz, pPunkt.getX(), pPunkt.getX(), pPunkt.getZ());
    }
    public Vector getPosition(){
        return new Vector (glo.gibX(), glo.gibY(), glo.gibZ());
    }
    public GLTextur getTexture(){
        return glo.gibTextur();
    }
    public float getX(){
        return glo.gibX();
    }
    public float getY(){
        return glo.gibY();
    }
    public float getZ(){
        return glo.gibZ();
    }
    public void delete(){
        glo.loesche();
        valid = false; //TODO dieses Objekt muss auf Scene zugreifen und sich löschen
    }
    public void rotate(double pDegrees, double pNX, double pNY, double pNZ, double pRX, double pRY, double pRZ){
        glo.rotiere(pDegrees, pNX, pNY, pNZ, pRX, pRY, pRZ);
    }
    public void rotate(double pDegrees, Vector pDirection, Vector pLocation){
        glo.rotiere(pDegrees, pDirection.toGLVektor(), pLocation.toGLVektor());
    }
    public void rotate(double pWX, double pWY, double pWZ){
        glo.setzeDrehung(pWX, pWY, pWZ);
    }
    public void setColor(double pR, double pG, double pB){
        glo.setzeFarbe(pR, pG, pB);
    }
    public void setScale(double pG){
        glo.setzeSkalierung(pG);
    }
    public void setScale(double pX, double pY, double pZ){
        glo.setzeSkalierung(pX, pY, pZ);
    }
    public void scale(double pG){
        glo.skaliere(pG);
    }
    public void scale(double pX, double pY, double pZ){
        glo.skaliere(pX, pY, pZ);
    }
    public void move(double pX, double pY, double pZ){
        glo.verschiebe(pX, pY, pZ);
    }
    public void move(Vector pVec){
        glo.verschiebe(pVec.toGLVektor());
    }
    public void setColor(String pFilename){
        glo.setzeTextur(pFilename);
    }
}
