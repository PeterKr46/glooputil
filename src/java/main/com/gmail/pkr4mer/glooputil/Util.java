package com.gmail.pkr4mer.glooputil;

/**
 * GloopUtil ist eine winzige Library, die zur Vereinfachung der Erstellung von Objekten in Gloop dient.
 * 
 * @author Peter Kramer
 * @version 0.1
 */

public abstract class Util
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
}