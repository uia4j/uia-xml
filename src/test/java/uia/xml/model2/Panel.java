package uia.xml.model2;

import uia.xml.AttrInfo;

public abstract class Panel {

    @AttrInfo(name = "id")
    private String id;

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + this.id;
    }

}
