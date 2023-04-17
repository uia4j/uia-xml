package uia.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

public final class XObject {

    public static <T extends Annotation> T getDeclaredAnnotation(Class<?> clz, Class<T> anClz) {
        for (Annotation an : clz.getDeclaredAnnotations()) {
            if (anClz.getName().equals(an.annotationType().getName())) {
                return (T) an;
            }
        }
        return null;
    }

    public static <T extends Annotation> T getDeclaredAnnotation(Field f, Class<T> anClz) {
        for (Annotation an : f.getDeclaredAnnotations()) {
            if (anClz.getName().equals(an.annotationType().getName())) {
                return (T) an;
            }
        }
        return null;
    }

    public static Field[] fields(Class<?> clz, Field[] fs1) {
        Field[] fs2 = clz.getDeclaredFields();

        Field[] result = new Field[fs1.length + fs2.length];
        for (int i = 0; i < fs1.length; i++) {
            result[i] = fs1[i];
        }
        for (int i = 0; i < fs2.length; i++) {
            result[fs1.length + i] = fs2[i];
        }

        Class<?> sclz = clz.getSuperclass();
        return sclz == null ? result : XObject.fields(sclz, result);
    }

    public static Object read(Field f, String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        if (f.getType() == long.class || f.getType() == Long.class) {
            return Long.valueOf(text);
        }
        else if (f.getType() == int.class || f.getType() == Integer.class) {
            return Integer.valueOf(text);
        }
        else if (f.getType() == short.class || f.getType() == Short.class) {
            return Short.valueOf(text);
        }
        else if (f.getType() == byte.class || f.getType() == Byte.class) {
            return Byte.valueOf(text);
        }
        else if (f.getType() == double.class || f.getType() == Double.class) {
            return Double.valueOf(text);
        }
        else if (f.getType() == float.class || f.getType() == Float.class) {
            return Float.valueOf(text);
        }
        else if (f.getType() == boolean.class || f.getType() == Boolean.class) {
            return Boolean.valueOf(text);
        }
        else if (f.getType() == BigDecimal.class) {
            return new BigDecimal(text);
        }

        return text;
    }
}
