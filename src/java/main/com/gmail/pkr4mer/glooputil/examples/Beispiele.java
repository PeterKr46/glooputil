package com.gmail.pkr4mer.glooputil.examples;

import GLOOP.GLLicht;
import GLOOP.GLObjekt;
import GLOOP.GLSchwenkkamera;
import com.gmail.pkr4mer.glooputil.GloopUtil;

import java.util.ArrayList;
import java.util.List;

public class Beispiele
{
    private static long lastSwitch = 0l;
    private static int visible = 0;

    public static void main(String[] args)
    {
        new GLLicht();
        new GLSchwenkkamera().zeigeAchsen(true);
        List<GLObjekt> e = new ArrayList<>();
        e.add(GloopUtil.erstelleEllipsoid(new double[]{0, 0, 0}, new double[]{5, 10, 5}));
        e.add(GloopUtil.erstelleEllipsoid(new double[]{5, 0, 0}, new double[]{10, 5, 10}));

        e.add(GloopUtil.erstelleQuader(new double[]{20, 0, 0}, new double[]{25, 10, 5}));

        e.add(GloopUtil.erstellePrismoid(new double[]{35, 0, 0}, new double[]{40, 10, 5}, 5, GloopUtil.Achse.Y));
        e.add(GloopUtil.erstellePrismoid(new double[]{40, 0, 0}, new double[]{45, 10, 5}, 5, GloopUtil.Achse.X));
        e.add(GloopUtil.erstellePrismoid(new double[]{45, 0, 0}, new double[]{50, 10, 5}, 5, GloopUtil.Achse.Z));

        e.add(GloopUtil.erstelleZylinder(new double[]{60, 0, 0}, new double[]{65, 10, 5}, GloopUtil.Achse.X));
        e.add(GloopUtil.erstelleZylinder(new double[]{65, 0, 0}, new double[]{70, 10, 5}, GloopUtil.Achse.Y));
        e.add(GloopUtil.erstelleZylinder(new double[]{70, 0, 0}, new double[]{75, 10, 5}, GloopUtil.Achse.Z));

        e.add(GloopUtil.erstelleKegel(new double[]{85, 0, 0}, new double[]{90, 10, 5}, GloopUtil.Achse.X));
        e.add(GloopUtil.erstelleKegel(new double[]{90, 0, 0}, new double[]{95, 10, 5}, GloopUtil.Achse.Y));
        e.add(GloopUtil.erstelleKegel(new double[]{95, 0, 0}, new double[]{100, 10, 5}, GloopUtil.Achse.Z));

        e.add(GloopUtil.erstelleKegelstumpf(new double[]{110, 0, 0}, new double[]{115, 10, 5}, 0.5f, GloopUtil.Achse.X));
        e.add(GloopUtil.erstelleKegelstumpf(new double[]{115, 0, 0}, new double[]{120, 10, 5}, 0.5f, GloopUtil.Achse.Y));
        e.add(GloopUtil.erstelleKegelstumpf(new double[]{120, 0, 0}, new double[]{125, 10, 5}, 0.5f, GloopUtil.Achse.Z));
        for( GLObjekt o : e ) o.setzeSichtbarkeit(false);
        while(true)
        {
            if(lastSwitch < System.currentTimeMillis() - 1000)
            {
                lastSwitch = System.currentTimeMillis();
                e.get(visible).setzeSichtbarkeit(false);
                visible++;
                if(visible >= e.size()) visible = 0;
                e.get(visible).setzeSichtbarkeit(true);
            }
        }
    }
}
