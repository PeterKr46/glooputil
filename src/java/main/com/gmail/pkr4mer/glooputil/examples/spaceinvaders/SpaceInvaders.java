package com.gmail.pkr4mer.glooputil.examples.spaceinvaders;

import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.Transform;
import com.gmail.pkr4mer.glooputil.object.renderable.Cube;
import com.gmail.pkr4mer.glooputil.object.renderable.Light;
import com.gmail.pkr4mer.glooputil.object.renderable.Sphere;
import com.gmail.pkr4mer.glooputil.object.renderable.VisibleTransform;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 3/4/14.
 */
public class SpaceInvaders
{
    public static void main(String[] args) throws Exception
    {
        final Scene scene = new Scene();
        VisibleTransform player = new Cube(Vector.zero(),1,scene,"Player","Player");
        scene.getCamera().setPosition(new Vector(0, 0, -50));
        scene.getCamera().lookAt(Vector.zero());
        new Light(Vector.zero(),scene,null,null).setBackgroundLight(50, 50, 50);
        player.addScript(new PlayerControl());
        player.setColor(256, 0, 0);
        scene.getCamera().addScript(
                new BehaviourScript() {
                    @Override
                    public void fixedUpdate() {
                        getTransform().setPosition(new Vector(0, 0, -50));
                    }

                    @Override
                    public RunPriority getRunPriority() {
                        return RunPriority.LOW;
                    }
                }
        );
        createEnemies(scene);
        scene.debug();
    }

    public static void createEnemies(Scene scene) throws Exception
    {
        Sphere base = new Sphere(new Vector(0,-30,0),1,scene,null,null);
        for( double x = -20; x <= 20; x+=3 )
        {
            VisibleTransform enemy = new Sphere(new Vector(x,20,0),1,scene,"enemy",null);
            enemy.setParent(base);
            if(Math.random() > 0.9)
            {
                enemy.addScript(
                        new BehaviourScript() {
                            int tl = 0;
                            @Override
                            public void fixedUpdate() {
                                tl++;
                                if(tl > 50 && tl < 52)
                                {
                                    getTransform().destroy();
                                    getTransform().getScene().debug();
                                }
                            }

                            @Override
                            public RunPriority getRunPriority() {
                                return RunPriority.LOW;
                            }
                        }
                );
                scene.log("Deleting -> " + enemy);
            }
        }
        base.addScript(new EnemyAI(-5,5,0.1));
    }
}
