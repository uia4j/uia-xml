package uia.xml;

import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XObjectWriter {

    public void run(XObjectW obj, OutputStream fos) throws XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos);
        writer.writeStartDocument();
        obj.write(writer);
        writer.writeEndDocument();
    }
}
