package uia.xml;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XObjectWriter {

    public void run(Object obj, OutputStream fos) throws XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(fos);
        writer.writeStartDocument();

        TagInfo tag = XObject.getDeclaredAnnotation(obj.getClass(), TagInfo.class);

        run(obj, tag.name(), writer);
        writer.writeEndDocument();
    }

    private void run(Object obj, String tagName, XMLStreamWriter writer) throws XMLStreamException {
        Class<?> clz = obj.getClass();

        writer.writeStartElement(tagName);
        Field[] fs = XObject.fields(clz, new Field[] {});
        // attributes
        for (Field f : fs) {
            f.setAccessible(true);
            AttrInfo attr = XObject.getDeclaredAnnotation(f, AttrInfo.class);
            if (attr == null) {
                continue;
            }

            String name = attr.name();
            if (name.isEmpty()) {
                name = f.getName();
            }
            try {
                writer.writeAttribute(name, "" + f.get(obj));
            }
            catch (Exception ex) {
                throw new XMLStreamException(ex);
            }
        }

        // elements
        for (Field f : fs) {
            f.setAccessible(true);
            TagInfo tag2 = XObject.getDeclaredAnnotation(f, TagInfo.class);
            if (tag2 != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = tag2.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                writer.writeStartElement(name);
                run(v, name, writer);
                writer.writeEndElement();
                continue;
            }

            TagListInfo tag3 = XObject.getDeclaredAnnotation(f, TagListInfo.class);
            if (tag3 != null) {
                List<?> vs = null;
                try {
                    vs = (List<?>) f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (!tag3.inline()) {
                    String name = tag3.name();
                    if (name.isEmpty()) {
                        name = f.getName();
                    }
                    writer.writeStartElement(name);
                }
                Map<String, TagListElem> mapping = new TreeMap<String, TagListElem>();
                for (TagListElem elem : tag3.elems()) {
                    mapping.put(elem.type().getName(), elem);
                }
                for (Object w : vs) {
                    TagListElem elem = mapping.get(w.getClass().getName());
                    if (elem != null) {
                        run(w, elem.name(), writer);
                    }
                }
                if (!tag3.inline()) {
                    writer.writeEndElement();
                }
                continue;
            }

            PropInfo prop = XObject.getDeclaredAnnotation(f, PropInfo.class);
            if (prop != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                String name = prop.name();
                if (name == null) {
                    name = f.getName();
                }
                writer.writeStartElement(name);
                if (v != null) {
                    if (prop.cdata()) {
                        writer.writeCData("" + v);
                    }
                    else {
                        writer.writeCharacters("" + v);
                    }
                }
                writer.writeEndElement();
                continue;
            }

            ContentInfo cont = XObject.getDeclaredAnnotation(f, ContentInfo.class);
            if (cont != null) {
                Object v = null;
                try {
                    v = f.get(obj);
                }
                catch (Exception ex) {
                    throw new XMLStreamException(ex);
                }

                if (v != null) {
                    if (cont.cdata()) {
                        writer.writeCData("" + v);
                    }
                    else {
                        writer.writeCharacters("" + v);
                    }
                }
            }
        }

        writer.writeEndElement();
    }
}
