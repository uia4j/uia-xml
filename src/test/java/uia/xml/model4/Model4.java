package uia.xml.model4;

import java.util.List;

import uia.xml.PropListInfo;
import uia.xml.TagInfo;

@TagInfo(name = "Simple")
public class Model4 {

    @PropListInfo(name = "Values")
    public List<String> values;

    @TagInfo(name = "Why")
    public Why why;

}
