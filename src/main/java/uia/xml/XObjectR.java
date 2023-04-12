package uia.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface XObjectR {

    public default XObjectR start(XMLStreamReader reader) throws XMLStreamException {
        return this;
    }

    public default XObjectR end(XMLStreamReader reader) throws XMLStreamException {
        return this;
    }

    public default XObjectR subStart(XMLStreamReader reader, String name) throws XMLStreamException {
        return this;
    }

    public default XObjectR subEnd(XMLStreamReader reader, String name) throws XMLStreamException {
        return this;
    }

}
