package com.gmail.pkr4mer.glooputil;

import GLOOP.*;

/**
 * GloopUtil ist eine winzige Library, die zur Vereinfachung der Erstellung von Objekten in Gloop dient.
 * 
 * @author Peter Kramer
 * @version 0.1
 */

public abstract class GloopUtil
{
	public static double[][] sortiere(double[] ecke1, double[] ecke2)
	{
		for(int i = 0; i < ecke1.length && i < ecke2.length; i++)	// Für alle Indexe, die beide Arrays haben
		{
			if( ecke1[i] > ecke2[i] )				                // Wenn der erste Wert größer als der zweite ist
			{
				double tmp = ecke1[i];				                // Lagere den ersten Wert aus
				ecke1[i] = ecke2[i];			                    // Ersetze in Array 1 durch den korrekten Wert
				ecke2[i] = tmp;					                    // Schreibe den ausgelagerten Wert in Array 2
			}
		}
		return new double[][]{ecke1,ecke2};				            // Gib die beiden sortierten Arrays zurück
	}

    public static void correctZ(GLKamera k)
    {
        GLVektor v = k.gibScheitelrichtung();
        v.z = 0;
        k.setzeScheitelrichtung(v);
    }

	public static GLQuader erstelleQuader(double[] ecke1, double[] ecke2)
	{
		double[][] ecken = sortiere(ecke1,ecke2);                   // Sortiere in kleine und große Ecke
		ecke1 = ecken[0];
		ecke2 = ecken[1];
		double breite = ecke2[0] - ecke1[0];                        // Berechne die Breite
		double hoehe = ecke2[1] - ecke1[1];                         // Berechne die Höhe
		double laenge = ecke2[2] - ecke1[2];                        // Berechne die Länge
        // Berechne den Mittelpunkt
		double[] mitte = new double[]{ecke1[0] + breite * 0.5, ecke1[1] + hoehe * 0.5, ecke1[2] + laenge * 0.5};
		return new GLQuader(mitte[0],mitte[1],mitte[2], breite, hoehe, laenge);
	}

	public static GLKugel erstelleEllipsoid(double[] ecke1, double[] ecke2)
	{
		double[][] ecken = sortiere(ecke1,ecke2);
		ecke1 = ecken[0];
		ecke2 = ecken[1];
		double breite = ecke2[0] - ecke1[0];
		double hoehe = ecke2[1] - ecke1[1];
		double laenge = ecke2[2] - ecke1[2];
		double[] mitte = new double[]{ecke1[0] + breite * 0.5, ecke1[1] + hoehe * 0.5, ecke1[2] + laenge * 0.5};
		double durchmesser = breite;
		if( hoehe < breite ) durchmesser = hoehe;
		if( laenge < durchmesser ) durchmesser = laenge;
		double radius = durchmesser * 0.5;
		GLKugel kugel = new GLKugel(mitte[0],mitte[1],mitte[2],radius);
		double skalX = (breite * 0.5)/radius;
		double skalY = (hoehe * 0.5)/radius;
		double skalZ = (laenge * 0.5)/radius;
		kugel.setzeSkalierung(skalX,skalY,skalZ);
		return kugel;
	}

    public static GLPrismoid erstelleZylinder(double[] ecke1, double[] ecke2,Achse ausrichtung)
    {
        return erstellePrismoid(ecke1,ecke2,100,ausrichtung);
    }

    public static GLPrismoid erstellePrismoid(double[] ecke1, double[] ecke2, int seiten, Achse ausrichtung)
    {
        return erstellePrismoid(ecke1,ecke2,seiten,1.0f,ausrichtung);
    }
    public static GLPrismoid erstellePrismoid(double[] ecke1, double[] ecke2, int seiten, float rad2, Achse ausrichtung)
    {
        double[][] ecken = sortiere(ecke1,ecke2);
        ecke1 = ecken[0];
        ecke2 = ecken[1];
        GLPrismoid prismoid = new GLPrismoid(0,0,0,1,rad2,seiten,1);
        if( ausrichtung == Achse.Y )
        {
            prismoid.drehe(270,0,0);
            double hoehe = ecke2[1] - ecke1[1];
            double breite = ecke2[0] - ecke1[0];
            double laenge = ecke2[2] - ecke1[2];
            double[] mitte = new double[]{ecke1[0] + breite * 0.5, ecke1[1] + hoehe * 0.5, ecke1[2] + laenge * 0.5};
            prismoid.setzePosition(mitte[0],mitte[1],mitte[2]);
            prismoid.skaliere(breite*0.5,laenge*0.5,hoehe);
            return prismoid;
        }
        else if( ausrichtung == Achse.Z )
        {
            double hoehe = ecke2[1] - ecke1[1];
            double breite = ecke2[0] - ecke1[0];
            double laenge = ecke2[2] - ecke1[2];
            double[] mitte = new double[]{ecke1[0] + breite * 0.5, ecke1[1] + hoehe * 0.5, ecke1[2] + laenge * 0.5};
            prismoid.setzePosition(mitte[0],mitte[1],mitte[2]);
            prismoid.skaliere(breite*0.5,hoehe*0.5,laenge);
        }
        else if( ausrichtung == Achse.X )
        {
            prismoid.drehe(0,90,0);
            double hoehe = ecke2[1] - ecke1[1];
            double breite = ecke2[0] - ecke1[0];
            double laenge = ecke2[2] - ecke1[2];
            double[] mitte = new double[]{ecke1[0] + breite * 0.5, ecke1[1] + hoehe * 0.5, ecke1[2] + laenge * 0.5};
            prismoid.setzePosition(mitte[0],mitte[1],mitte[2]);
            prismoid.skaliere(laenge*0.5,hoehe*0.5,breite);
        }
        return prismoid;
    }

    public static GLPrismoid erstelleKegel(double[] ecke1, double[] ecke2, Achse ausrichtung)
    {
        return erstelleKegelstumpf(ecke1,ecke2,0,ausrichtung);
    }

    public static GLPrismoid erstelleKegelstumpf(double[] ecke1, double[] ecke2, float rad2, Achse ausrichtung)
    {
        return erstellePrismoid(ecke1, ecke2, 100, rad2, ausrichtung);
    }

    public static enum Achse
    {
        X,Y,Z
    }
}