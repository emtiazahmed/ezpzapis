package com.ezpzapis.commons.util;
import java.lang.reflect.Field;

public class TypeValidator {

    public static boolean validate(Class clazz, String fieldName, String fieldValue) {
        try {
            Field field = getField(clazz, fieldName);
            switch (field.getType().getSimpleName()) {
                case "String":
                    if( (fieldValue.startsWith("'") && fieldValue.endsWith("'"))
                        || (fieldValue.startsWith("\"") && fieldValue.endsWith("")) )
                        return true;
                    else
                        throw new RuntimeException("Text field values must be enclosed in quotes. E.g, '' or \"\"");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Field with name %s does not exist", fieldName), e);
        }
        return false;
    }

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        String[] fields = fieldName.split("\\.");
        for( int i = 0; i < fields.length; i++) {
            if ( field == null)
                field = clazz.getDeclaredField(fields[i]);
            else
                field = field.getType().getDeclaredField(fields[i]);
        }
        return field;
    }
}
