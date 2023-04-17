package uia.xml.model3;

import uia.xml.PropInfo;
import uia.xml.TagInfo;

@TagInfo(name = "Elem1")
public class Elem1 {

    @PropInfo(name = "Value1")
    public String value1;

    @PropInfo(name = "Value2")
    public String value2;

    @TagInfo(name = "Elem2")
    public Elem2 elem2;

    @TagInfo(name = "Elem3")
    public Elem3 elem3;
}
