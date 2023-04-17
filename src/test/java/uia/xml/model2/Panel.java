package uia.xml.model2;

import uia.xml.AttrInfo;

public abstract class Panel {

    @AttrInfo(name = "id")
    private String id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + this.id;
    }

}
