package uia.xml.model2;

import uia.xml.ContentInfo;
import uia.xml.TagInfo;

@TagInfo(name = "Picture")
public class Picture extends Panel {

    @ContentInfo
    private String path;
}
