package com.gmail.pkr4mer.glooputil.examples.spaceinvaders;

import com.gmail.pkr4mer.glooputil.object.renderable.Sphere;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

/**
 * Created by peter on 3/6/14.
 */
public class EnemyShootScript extends BehaviourScript
{
    int sinceLast = 0;
    @Override
    public void fixedUpdate()
    {
        sinceLast++;
        if(sinceLast > 20)
        {
            fire();
            sinceLast = 0;
        }
    }

    public void fire()
    {
        Sphere shot = new Sphere(getTransform().getAbsolutePosition(),0.1,getTransform().getScene(),"Shot","Shot");
        shot.addScript(
                new BehaviourScript() {
                    @Override
                    public void fixedUpdate() {
                        if(getTransform().getY() < 0)
                        {
                            getTransform().destroy();
                        }
                        else
                        {
                            getTransform().move(0,-0.5,0);
                        }
                    }

                    @Override
                    public RunPriority getRunPriority() {
                        return RunPriority.LOW;
                    }
                }
        );
    }

    @Override
    public RunPriority getRunPriority()
    {
        return RunPriority.LOW;
    }
}
