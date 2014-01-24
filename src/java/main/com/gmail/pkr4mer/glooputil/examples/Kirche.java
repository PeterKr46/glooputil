package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.GloopUtil;

/**
 * Created by peter on 1/24/14.
 */
public class Kirche
{
    public static void main(String[] args)
    {
        GLLicht licht = new GLLicht(0,10,0);
        licht.setzeAbschwaechung(1);
        licht.setzeHintergrundlicht(50, 50, 50);
        GLSchwenkkamera k = new GLSchwenkkamera();
        k.zeigeAchsen(true);
        GloopUtil.erstelleQuader(new double[]{0, 0, 0}, new double[]{10, 5, 7.5});
        GloopUtil.erstelleZylinder(new double[]{7, 0, 0}, new double[]{13, 10, 7.5}, GloopUtil.Achse.Y);
        GloopUtil.erstelleKegel(new double[]{7,10,0}, new double[]{13,15,7.5}, GloopUtil.Achse.Y).drehe(180, 0, 0);
        GloopUtil.erstellePrismoid(new double[]{0, 4, 0}, new double[]{10, 8, 7.5}, 3, GloopUtil.Achse.X);
        GloopUtil.erstelleQuader(new double[]{-20, -1, -20}, new double[]{20, 0, 20});
        GloopUtil.erstelleQuader(new double[]{-20,0,-20}, new double[]{20,2,-19});
        GloopUtil.erstelleQuader(new double[]{-20, 0, 20}, new double[]{20, 2, 19});
        GloopUtil.erstelleEllipsoid(new double[]{9,14,2.75},new double[]{11,16,4.75});
    }
}