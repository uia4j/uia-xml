package uia.xml.model2;

import java.util.ArrayList;

import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo
public class Picture extends Panel {

    @TagListInfo(elems = {
            @TagListElem(name = "picUnit", type = PicUnit.class)
    }, inline = true)
    private ArrayList<PicUnit> picUnits;

    public Picture() {
        this.picUnits = new ArrayList<PicUnit>();
    }

    @TagInfo
    public static class PicUnit {

        @PropInfo
        private int x;

        @PropInfo
        private int y;

        @PropInfo
        private int width;

        @PropInfo
        private int height;
    }
}
