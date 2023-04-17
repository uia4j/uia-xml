package uia.xml.model3;

import java.util.ArrayList;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo(name = "Elem5")
public class Elem5 {

    @TagListInfo(
            elems = { @TagListElem(name = "Elem2", type = Elem2.class) })
    public ArrayList<Elem2> elem2s;

    public Elem5() {
        this.elem2s = new ArrayList<Elem2>();
    }
}
