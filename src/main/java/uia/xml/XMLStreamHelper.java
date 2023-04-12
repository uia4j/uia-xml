package uia.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLStreamHelper {

    public static void _attr(XMLStreamWriter writer, String name, String value) throws XMLStreamException {
        writer.writeAttribute(name, value);
    }

    public static void _chars(XMLStreamWriter writer, String name, String value) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(value);
        writer.writeEndElement();

    }

    public static void _cdata(XMLStreamWriter writer, String name, String value) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCData(value);
        writer.writeEndElement();
    }

    public static void _elem(XMLStreamWriter writer, XObjectW obj) throws XMLStreamException {
        obj.write(writer);
    }

}
