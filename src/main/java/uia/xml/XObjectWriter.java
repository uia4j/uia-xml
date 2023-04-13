package uia.xml;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XObjectWriter {

    public void run(Object obj, OutputStream fos) throws XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos);
        writer.writeStartDocument();
        run(obj, writer);
        writer.writeEndDocument();
    }

    private void run(Object obj, XMLStreamWriter writer) throws XMLStreamException {
        Class<?> clz = obj.getClass();

        TagInfo tag1 = clz.getDeclaredAnnotation(TagInfo.class);
        if (tag1 == null) {
            throw new XMLStreamException("No TagInfo definition on " + obj.getClass().getName());
        }

        writer.writeStartElement(tag1.name());

        Field[] fs = clz.getDeclaredFields();
        // attributes
        for (Field f : fs) {
            AttrInfo attr = f.getDeclaredAnnotation(AttrInfo.class);
            if (attr == null) {
                continue;
            }

            try {
                writer.writeAttribute(attr.name(), "" + f.get(obj));
            }
            catch (Exception ex) {
                throw new XMLStreamException(ex);
            }
        }
        // elements
        for (Field f : fs) {
            TagInfo tag2 = f.getDeclaredAnnotation(TagInfo.class);
            if (tag2 != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (!tag2.name().isEmpty()) {
                    writer.writeStartElement(tag2.name());
                }
                if (v instanceof List) {
                    List<?> vs = (List<?>) v;
                    for (Object w : vs) {
                        run(w, writer);
                    }
                }
                else {
                    run(v, writer);
                }
                if (!tag2.name().isEmpty()) {
                    writer.writeEndElement();
                }
                continue;
            }

            ContentInfo cont = f.getDeclaredAnnotation(ContentInfo.class);
            if (cont != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (!cont.name().isEmpty()) {
                    writer.writeStartElement(cont.name());
                }
                if (v != null) {
                    if (cont.cdata()) {
                        writer.writeCData("" + v);
                    }
                    else {
                        writer.writeCharacters("" + v);
                    }
                }
                if (!cont.name().isEmpty()) {
                    writer.writeEndElement();
                }
            }

        }

        writer.writeEndElement();
    }
}
