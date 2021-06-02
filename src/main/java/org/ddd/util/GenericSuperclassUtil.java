package org.ddd.util;

import org.ddd.event.domain.EventSubscriber;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Michael
 */
public class GenericSuperclassUtil {
    public static Class<?> getInterfaceT(Class aClass, int index) {
        Type[] types = aClass.getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);

    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }
}
