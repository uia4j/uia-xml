package uia.xml.model3;

import java.lang.reflect.Type;

import uia.xml.AttrInfo;
import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.XObjectValue;

@TagInfo(name = "Elem2")
public class Elem2 {

    @AttrInfo(name = "id")
    public String id;

    @PropInfo(name = "Value1", parser = BoolValue.class)
    public boolean value1;

    @PropInfo(name = "Value2")
    public String value2;

    public static class BoolValue implements XObjectValue {

        @Override
        public Object read(Type type, String text) {
            return "Y".equals(text) ? true : false;
        }

        @Override
        public String write(Object value) {
            boolean b = (Boolean) value;
            return b ? "Y" : "N";
        }

    }

}
