package com.gmail.pkr4mer.glooputil.object.scripting;

import com.gmail.pkr4mer.glooputil.object.GUObject;
import com.gmail.pkr4mer.glooputil.position.Vector;

/**
 * Created by peter on 1/27/14.
 */
public class FollowOffsetScript extends FollowScript
{
    private Vector offset;

    public FollowOffsetScript(GUObject follow, Vector offset) {
        super(follow);
        this.offset = offset;
    }

    @Override
    public void fixedUpdate() {
        super.fixedUpdate();
        getGUObject().move(offset);
    }
}
