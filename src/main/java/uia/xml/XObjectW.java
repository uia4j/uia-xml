package uia.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XObjectW {

    public default XObjectW start(XMLStreamWriter writer) throws XMLStreamException {
        return this;
    }

    public default XObjectW end(XMLStreamWriter writer) throws XMLStreamException {
        return this;
    }

    public default XObjectW subStart(XMLStreamWriter writer, String name) throws XMLStreamException {
        return this;
    }

    public default XObjectW subEnd(XMLStreamWriter writer, String name) throws XMLStreamException {
        return this;
    }

}
