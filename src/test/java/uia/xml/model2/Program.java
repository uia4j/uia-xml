package uia.xml.model2;

import java.util.ArrayList;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo(name = "Program")
public class Program {

    @TagListInfo(
            inline = true,
            elems = {
                    @TagListElem(name = "Picture", type = Picture.class),
                    @TagListElem(name = "Text", type = Text.class),
                    @TagListElem(name = "Calendar", type = Calendar.class),
                    @TagListElem(name = "File", type = File.class) })
    public ArrayList<Panel> panels;

    public Program() {
        this.panels = new ArrayList<Panel>();
    }

    @Override
    public String toString() {
        return this.panels.toString();
    }

}
