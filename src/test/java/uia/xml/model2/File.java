package uia.xml.model2;

import uia.xml.ContentInfo;
import uia.xml.TagInfo;

@TagInfo
public class File extends Panel {

    @ContentInfo
    private String path;

    public String getPath() {
        return this.path;
    }
}
