package com.gmail.pkr4mer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//Based on DarkBladee12's implementation
public class ReflectionUtilities {
 
    /**
    * sets a value of an {@link Object} via reflection
    *
    * @param instance instance the class to use
    * @param fieldName the name of the {@link java.lang.reflect.Field} to modify
    * @param value the value to set
    * @throws Exception
    */
    public static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    /**
    * get a value of an {@link Object}'s {@link java.lang.reflect.Field}
    *
    * @param instance the target {@link Object}
    * @param fieldName name of the {@link java.lang.reflect.Field}
    * @return the value of {@link Object} instance's {@link java.lang.reflect.Field} with the
    *        name of fieldName
    * @throws Exception
    */
    public static Object getValue(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
    
    /**
     * Internal method, used as shorthand to grab our method in a nice friendly manner
     * @param cl
     * @param method
     * @return Method (or null)
     */
    public static Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {
            if(m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }

    public static void debugFields(Class<?> cls)
    {
        for( Field field : cls.getDeclaredFields())
        {
            System.out.println(" - " + field.getName() + "@" + cls.getName());
        }
    }
 
}