package com.gmail.pkr4mer.util;

import com.sun.istack.internal.NotNull;


/**
 * Created by peter on 1/7/14.
 */
public class Pair<T1,T2>
{
    @NotNull
    private T1 a;
    @NotNull
    private T2 b;

    public Pair(T1 a, T2 b)
    {
        this.a = a;
        this.b = b;
    }

    public boolean compare(Pair<T1,T2> otherPair)
    {
        return b.equals(otherPair.b) && a.equals(otherPair.a);
    }

    public T1 getA()
    {
        return a;
    }

    public T2 getB()
    {
        return b;
    }

    public void setA(T1 a)
    {
        this.a = a;
    }

    public void setB(T2 b)
    {
        this.b = b;
    }

    public Class getAType()
    {
        return a.getClass();
    }

    public Class getBType()
    {
        return b.getClass();
    }
}