package com.ezpzapis.commons.util;


import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class EntityUtils {
    public static List<Field> getFields(Class clazz) {
        List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(clazz,
                field -> fields.add(field),
                field -> {
                    return field.getModifiers() == Modifier.STATIC ? false : true;
                });
        return fields;
    }
}
