package com.gmail.pkr4mer.glooputil.object;

import java.util.List;

/**
 * Created by peter on 1/30/14.
 */
public interface ObjectHolder
{
    public boolean addChild(ObjectHolder child);

    public ObjectHolder getParent();

    public boolean setParent(ObjectHolder parent) throws Exception;

    public boolean isParentOf(ObjectHolder other);

    public boolean isChildOf(ObjectHolder other);

    public List<ObjectHolder> getChildren();

    public boolean removeChild(ObjectHolder child);

    public abstract void debug();

    public abstract void debug(String prefix);
}
