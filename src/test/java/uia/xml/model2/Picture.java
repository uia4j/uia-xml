package uia.xml.model2;

import java.util.ArrayList;
import java.util.List;

import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo
public class Picture extends Panel {

    @TagListInfo(elems = {
            @TagListElem(name = "picUnit", type = PicUnit.class)
    }, inline = true)
    private List<PicUnit> picUnits;

    public Picture() {
        this.picUnits = new ArrayList<PicUnit>();
    }

    public Picture(PicUnit... picUnits) {
        this.picUnits = new ArrayList<PicUnit>();
        for (PicUnit pu : picUnits) {
            this.picUnits.add(pu);
        }
    }

    public List<PicUnit> getPicUnits() {
        return this.picUnits;
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

        public PicUnit() {
        }

        public PicUnit(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }

        public int getX() {
            return this.x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return this.y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }
}
