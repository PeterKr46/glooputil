package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.collider.Collider;


/**
 * Created by peter on 1/25/14.
 */
public abstract class BehaviourScript
{
    private boolean started = false;
    private Transform transform;

    public BehaviourScript() {}

    public final void setTransform(Transform o)
    {
        this.transform = o;
    }

    public final void onUpdate()
    {
        if(!started) onStart();
        started = true;
        fixedUpdate();
    }

    public abstract void fixedUpdate();

    public void onStart()
    {

    }

    public final Transform getTransform()
    {
        return transform;
    }

    public String getTypeName()
    {
        return this.getClass().getName();
    }

    public abstract RunPriority getRunPriority();

    public void onCollisionStay(Collider c)
    {

    }

    public void onCollisionEnter(Collider c)
    {

    }

    public void onCollisionExit(Collider c)
    {

    }

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
