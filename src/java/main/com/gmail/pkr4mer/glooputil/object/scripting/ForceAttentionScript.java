package com.gmail.pkr4mer.glooputil.object.scripting;

/**
 * Created by peter on 1/27/14.
 */
public class ForceAttentionScript extends GUScript
{

    @Override
    public void fixedUpdate() {
        getGUObject().getScene().getCamera().setTargetPoint(getGUObject().getPosition());
    }

    @Override
    public String getTypeName() {
        return "ForceAttention";
    }

    @Override
    public RunPriority getRunPriority() {
        return RunPriority.HIGH;
    }
}
