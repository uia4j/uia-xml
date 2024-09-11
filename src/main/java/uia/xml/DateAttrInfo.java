package uia.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DateAttrInfo {

    /**
     * The attribute name. Default is name of annotated variable.
     *
     * @return The attribute name.
     */
    String name() default "";

    String format() default "yyyy-MM-dd HH:mm:ss";

}
