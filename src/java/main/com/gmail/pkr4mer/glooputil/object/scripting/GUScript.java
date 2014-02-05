package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.Transform;


/**
 * Created by peter on 1/25/14.
 */
public abstract class GUScript
{

    private Transform guObject;

    public GUScript() {}

    public final void setGUObject(Transform o)
    {
        this.guObject = o;
    }

    public abstract void fixedUpdate();

    public final Transform getGUObject()
    {
        return guObject;
    }

    public String getTypeName()
    {
        return "DefaultName";
    }

    public abstract RunPriority getRunPriority();

    /*public void onCollisionStay(GUCollider c)
    {

    }

    public void onCollisionEnter(GUCollider c)
    {

    }

    public void onCollisionExit(GUCollider c)
    {

    }*/

    public static enum RunPriority
    {
        LOW, MEDIUM, HIGH, HIGHEST;
        public int getValue()
        {
            switch(this)
            {
                case LOW: return 0;
                case MEDIUM: return 1;
                case HIGH: return 2;
                case HIGHEST: return 3;
                default: return 0;
            }
        }

        public static int[] getValues()
        {
            return new int[]{0,1,2,3};
        }
    }
}
