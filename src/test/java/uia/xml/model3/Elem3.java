package uia.xml.model3;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.TagInfo;

@TagInfo(name = "Elem3")
public class Elem3 {

    @AttrInfo(name = "name")
    public String name;

    @ContentInfo
    public String value2;
}
