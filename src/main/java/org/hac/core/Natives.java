package org.hac.core;

import org.hac.exception.HacException;

import java.lang.reflect.Method;

public class Natives {
    public Environment environment(Environment env) {
        appendNatives(env);
        return env;
    }

    protected void appendNatives(Environment env) {
        append(env, "print", Natives.class, "print", Object.class);
        append(env, "length", Natives.class, "length", String.class);
        append(env, "toInt", Natives.class, "toInt", Object.class);
        append(env, "currentTime", Natives.class, "currentTime");
    }

    protected void append(Environment env, String name, Class<?> clazz,
                          String methodName, Class<?>... params) {
        Method m;
        try {
            m = clazz.getMethod(methodName, params);
        } catch (Exception e) {
            throw new HacException("cannot find a native function: " + methodName);
        }
        env.put(name, new NativeFunction(methodName, m));
    }

    public static int print(Object obj) {
        System.out.println(obj.toString());
        return 0;
    }

    public static int length(String s) {
        return s.length();
    }

    public static int toInt(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        else if (value instanceof Integer) {
            return (Integer) value;
        }
        else {
            throw new NumberFormatException(value.toString());
        }
    }

    private static final long startTime = System.currentTimeMillis();

    public static int currentTime() {
        return (int) (System.currentTimeMillis() - startTime);
    }
}