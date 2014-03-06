package com.gmail.pkr4mer.glooputil.object;

import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;

import java.util.List;

/**
 * Created by peter on 1/30/14.
 */
public interface ScriptHolder
{
    public void runScripts();
    public BehaviourScript getScript(String id);
    public boolean addScript(BehaviourScript script);
    public boolean removeScript(BehaviourScript script);
    public List<String> getScripts();
}
