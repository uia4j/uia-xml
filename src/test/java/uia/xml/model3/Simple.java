package uia.xml.model3;

import java.util.ArrayList;

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
    public ArrayList<Object> elem4s;

    @XmlInfo(name = "Elem5")
    public String elem5;

    public Simple() {
        this.elem4s = new ArrayList<Object>();
    }

}
