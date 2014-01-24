package com.gmail.pkr4mer.glooputil;

import GLOOP.GLObjekt;


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

    private void setzePosition(double px, double py, double pz){
        GLO.setzePosition(px, py, pz);
    }
    /*private void setzePosition(Vector pPosition){
        GLO.setzePosition(pPosition);
    }*/
}
