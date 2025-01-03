/*******************************************************************************
 * Copyright 2023 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * The XML Object helper methods.
 *
 * @author ks026400
 *
 */
public final class XObjectHelper {

    /**
     * Convert the text to correct type. Types include Long, Integer, Short, Byte, Double, Float, Boolean and BigDecimal.
     *
     * @param f The field.
     * @param text The value text.
     * @return The value with correct type.
     */

    public static Object read(Type type, String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        if (type == null) {
            return text;
        }

        if (type == long.class || type == Long.class) {
            return Long.valueOf(text);
        }
        else if (type == int.class || type == Integer.class) {
            return Integer.valueOf(text);
        }
        else if (type == short.class || type == Short.class) {
            return Short.valueOf(text);
        }
        else if (type == byte.class || type == Byte.class) {
            return Byte.valueOf(text);
        }
        else if (type == double.class || type == Double.class) {
            return Double.valueOf(text);
        }
        else if (type == float.class || type == Float.class) {
            return Float.valueOf(text);
        }
        else if (type == boolean.class || type == Boolean.class) {
            return Boolean.valueOf(text);
        }
        else if (type == BigDecimal.class) {
            return new BigDecimal(text);
        }

        return text;
    }

    /**
     * Return the annotation instance.
     *
     * @param clz The class type.
     * @param anClz The annotation type.
     * @return The annotation instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getDeclaredAnnotation(Class<?> clz, Class<T> anClz) {
        for (Annotation an : clz.getDeclaredAnnotations()) {
            if (anClz.getName().equals(an.annotationType().getName())) {
                return (T) an;
            }
        }
        return null;
    }

    /**
     * Return the annotation instance.
     *
     * @param f The field.
     * @param anClz The annotation type.
     * @return The annotation instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getDeclaredAnnotation(Field f, Class<T> anClz) {
        for (Annotation an : f.getDeclaredAnnotations()) {
            if (anClz.getName().equals(an.annotationType().getName())) {
                return (T) an;
            }
        }
        return null;
    }

    /**
     * Return fields including in the parent class.
     *
     * @param clz The class type to be searched.
     * @return The fields in the class.
     */
    public static Field[] fields(Class<?> clz) {
        return fields(clz, new Field[] {});
    }

    private static Field[] fields(Class<?> clz, Field[] fs1) {
        Field[] fs2 = clz.getDeclaredFields();

        Field[] result = new Field[fs1.length + fs2.length];
        for (int i = 0; i < fs1.length; i++) {
            result[i] = fs1[i];
        }
        for (int i = 0; i < fs2.length; i++) {
            result[fs1.length + i] = fs2[i];
        }

        Class<?> sclz = clz.getSuperclass();
        return sclz == null ? result : fields(sclz, result);
    }
}
