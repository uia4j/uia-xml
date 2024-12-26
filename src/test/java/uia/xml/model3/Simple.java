package uia.xml.model3;

import java.util.List;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;
import uia.xml.XmlInfo;

@TagInfo(name = "Simple")
public class Simple {

    @TagInfo(name = "Elem1")
    public Elem1 elem1;

    @TagInfo(name = "Elem2")
    public Elem2 elem2;

    @TagListInfo(
            name = "Elem4List",
            elems = {
                    @TagListElem(name = "Elem2", type = Elem2.class),
                    @TagListElem(name = "Elem4", type = Elem4.class)
            })
    public List<Object> elem4s;

    @XmlInfo(name = "Elem5")
    public String elem5;
}
