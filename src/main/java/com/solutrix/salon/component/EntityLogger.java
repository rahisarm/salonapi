package com.solutrix.salon.component;

import java.lang.reflect.Field;

public class EntityLogger {
    public String entityToString(Object obj) {
        StringBuilder result = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();

        result.append(obj.getClass().getName()).append(" {");
        for (Field field : fields) {
            field.setAccessible(true);  // Allows access to private fields
            try {
                result.append(field.getName()).append(" = ").append(field.get(obj)).append(", ");
            } catch (IllegalAccessException e) {
                result.append(field.getName()).append(" = ").append("N/A, ");
            } catch (NullPointerException e){
                result.append(field.getName()).append(" = ").append("NULL, ");
            }

        }
        result.append("}");
        return result.toString();
    }
}
