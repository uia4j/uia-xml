package uia.xml.model2;

import java.util.ArrayList;
import java.util.List;

import uia.xml.TagInfo;
import uia.xml.TagListInfo;

@TagInfo(name = "Program")
public class Program {

    @TagListInfo(classes = { Picture.class, Text.class, Calendar.class })
    public List<Panel> panels;

    public Program() {
        this.panels = new ArrayList<>();
    }

}
