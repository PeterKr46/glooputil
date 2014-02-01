package com.gmail.pkr4mer.glooputil.object;

import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;

import java.util.List;

/**
 * Created by peter on 1/30/14.
 */
public interface ScriptHolder
{
    public void runScripts();
    public GUScript getScript(String id);
    public boolean addScript(GUScript script);
    public boolean removeScript(GUScript script);
    public List<String> getScripts();
}
