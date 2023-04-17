package uia.xml.nodes;

import java.lang.reflect.Field;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.XObject;

public class PropNode implements Node {

    private final String name;

    private final Object owner;

    private final Field f;

    public PropNode(String name) {
        this.name = name;
        this.owner = null;
        this.f = null;
    }

    public PropNode(String name, Object owner, Field f) {
        this.name = name;
        this.owner = owner;
        this.f = f;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        Object value = null;
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.END_ELEMENT) {
                break;
            }
            value = XObject.read(this.f, xmlReader.getText());
            if (this.owner != null) {
                this.f.set(this.owner, value);
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
