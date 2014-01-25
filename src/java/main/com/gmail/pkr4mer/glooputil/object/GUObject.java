package com.gmail.pkr4mer.glooputil.object;

import GLOOP.GLObjekt;
import GLOOP.GLTextur;
import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.position.Vector;


/**
 * Created by Jonas on 24.01.14.
 */
public class GUObject {

    protected GLObjekt glo;
    protected Scene scene;
    protected Vector rotation;

    protected String tag;
    protected String name;

    protected boolean valid = true;

    public GUObject(GLObjekt g, Scene s, String t, String n, Vector dir)
    {
        glo = g;
        scene = s;
        rotation = dir;
        tag = t;
        name = n;
    }

    public String getName()
    {
        return name;
    }

    public String getTag()
    {
        return tag;
    }

    public Scene getScene()
    {
        return scene;
    }

    public void setPosition(double pX, double pY, double pZ){
        glo.setzePosition(pX, pY, pZ);
    }
    public void setPosition(Vector pPosition){
        glo.setzePosition(pPosition.getX(), pPosition.getY(), pPosition.getZ());
    }
    public void rotate(double pDx, double pDy, double pDz){
        glo.drehe(pDx, pDy, pDz);
        rotation.add(new Vector(pDx,pDy,pDz));
        fixRotation();
    }
    public void turnAround(Vector rot, Vector pt){
        glo.drehe(rot.getX(), rot.getY(), rot.getZ(), pt.getX(), pt.getY(), pt.getZ());
    }
    public void turnAround(double pDx, double pDy, double pDz, Vector pt){
        glo.drehe(pDx, pDy, pDz, pt.getX(), pt.getX(), pt.getZ());
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
    public void setRotation(double pDx, double pDy, double pDz){
        glo.setzeDrehung(pDx, pDy, pDz);
        rotation = new Vector(pDx, pDy, pDz);
        fixRotation();
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
    public void setTexture(String pFilename){
        glo.setzeTextur(pFilename);
    }

    public Vector getRotation()
    {
        return rotation.clone();
    }

    public void setRotation(Vector rotation)
    {
        glo.setzeDrehung(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Vector getForward()
    {
        return new Vector(
                Math.tan(Math.toRadians(rotation.getX())),
                Math.tan(Math.toRadians(rotation.getZ())),
                Math.tan(Math.toRadians(rotation.getY()))
            );
    }

    public void forward(double distance)
    {
        move(getForward().multiply(distance));
    }

    private void fixRotation()
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
    public void resetDisplayList(){
        glo.resetDisplayliste();
    }
    public void setQuality(int pQ){
        glo.setzeQualitaet(pQ);
    }
    public void setVisible(boolean pV){
        glo.setzeSichtbarkeit(pV);
    }
    /*public void setTexture(GUTexture pTex){
        glo.setzeTextur(pTex);
    }*/
    public void setLance(double pR, double pG, double pB, int pStrength){
        glo.setzeGlanz(pR, pG, pB, pStrength);
    }
    public void setMaterial(float[][] pm){
        glo.setzeMaterial(pm);
    }
    public void setSelfIlluminating(double pR, double pG, double pB){
        glo.setzeSelbstleuchten(pR, pG, pB);
    }
}
