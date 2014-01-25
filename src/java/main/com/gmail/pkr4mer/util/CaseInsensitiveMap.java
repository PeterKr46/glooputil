package com.gmail.pkr4mer.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by peter on 1/24/14.
 */


public class CaseInsensitiveMap<T>
{
    private HashMap<String,T> backend = new HashMap<>();

    public T put(String key, T value)
    {
        return backend.put(key.toLowerCase(), value);
    }

    public T get(String key)
    {
        return backend.get(key.toLowerCase());
    }

    public boolean containsKey(String key)
    {
        return backend.containsKey(key.toLowerCase());
    }

    public List<String> keySet()
    {
        ArrayList<String> keys = new ArrayList<>(backend.keySet());
        //lowercase
        for( int i = 0; i < keys.size(); i++ )
        {
            keys.set(i,keys.get(i).toLowerCase());
        }
        return keys;
    }

    public Collection<T> values()
    {
        return backend.values();
    }

    public T remove(String key)
    {
        return backend.remove(key.toLowerCase());
    }
}
