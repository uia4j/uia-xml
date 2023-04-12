package uia.xml;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class XObjectReader {

    public void run(XObjectR obj, InputStream fis) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        // https://rules.sonarsource.com/java/RSPEC-2755
        // prevent xxe
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis);
        int eventType = reader.getEventType();
        while (reader.hasNext()) {
            TagInfo tag = obj.getClass().getDeclaredAnnotation(TagInfo.class);
            if (tag == null) {
                throw new XMLStreamException("No TagInfo definition in " + obj.getClass().getName());
            }

            eventType = reader.next();
            if (eventType == XMLEvent.START_ELEMENT) {
                String name = reader.getName().getLocalPart();
                if (name.equals(tag.name())) {
                    obj = obj.start(reader);
                }
                else {
                    XObjectR sub = obj.subStart(reader, name);
                    if (sub != obj) {
                        sub.start(reader);
                        obj = sub;
                    }
                }
            }
            else if (eventType == XMLEvent.END_ELEMENT) {
                String name = reader.getName().getLocalPart();
                if (name.equals(tag.name())) {
                    obj = obj.end(reader);
                }
                else {
                    obj = obj.subEnd(reader, name);
                }
            }
        }
    }
}
