package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLTextur;
import com.gmail.pkr4mer.glooputil.position.Vector;


/**
 * Created by Anwender on 24.01.14.
 */
public class GUObject {
    private GLObjekt GLO;
    private String tag;
    private String name;

    public GUObject(GLObjekt g, String t, String n) {
        GLO = g;
        tag = t;
        name = n;
    }

    private void setPosition(double px, double py, double pz){
        GLO.setzePosition(px, py, pz);
    }
    private void setPosition(Vector pPosition){
        GLO.setzePosition(pPosition.getX(), pPosition.getY(), pPosition.getZ());
    }
    private void turn(double pWx, double pWy, double pWz){
        GLO.drehe(pWx, pWy, pWz);
    }
    private void turn(double pWx, double pWy, double pWz, double px, double py, double pz){
        GLO.drehe(pWx, pWy, pWz, px, py, pz);
    }
    private void turn(double pWx, double pWy, double pWz, Vector pPunkt){
        GLO.drehe(pWx, pWy, pWz, pPunkt.getX(), pPunkt.getX(), pPunkt.getZ());
    }
    private Vector getPosition(){
        return new Vector (GLO.gibX(), GLO.gibY(), GLO.gibZ());
    }
    private GLTextur getTexture(){
        return GLO.gibTextur();
    }
    private float getX(){
        return GLO.gibX();
    }

}
