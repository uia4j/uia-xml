package uia.xml.nodes;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListInfo;
import uia.xml.XObject;

public class TagNode implements Node {

    private final String name;

    private final Object obj;

    public TagNode(String name, Object obj) {
        this.name = name;
        this.obj = obj;
    }

    @Override
    public Object read(XMLStreamReader xmlReader) throws Exception {
        Class<?> clz = this.obj.getClass();
        Field[] fs = XObject.fields(clz, new Field[] {});

        Map<String, Node> subs = new TreeMap<String, Node>();
        ContentInfo cont = null;
        Field contF = null;
        TagListNode inline = null;
        for (Field f : fs) {
            f.setAccessible(true);
            AttrInfo attr = f.getDeclaredAnnotation(AttrInfo.class);
            if (attr != null) {
                String name = attr.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                String text = xmlReader.getAttributeValue(null, name);
                f.set(this.obj, XObject.read(f, text));
                continue;
            }

            TagInfo tag = f.getDeclaredAnnotation(TagInfo.class);
            if (tag != null) {
                String name = tag.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                Object value = f.getType().newInstance();
                f.set(this.obj, value);
                subs.put(name, new TagNode(name, value));
                continue;
            }

            TagListInfo list = f.getDeclaredAnnotation(TagListInfo.class);
            if (list != null) {
                @SuppressWarnings("unchecked")
                List<Object> value = (List<Object>) f.getType().newInstance();
                f.set(this.obj, value);
                if (list.inline()) {
                    inline = new TagListNode(this.name, value, list);
                }
                else {
                    String name = list.name();
                    if (name.isEmpty()) {
                        name = f.getName();
                    }
                    subs.put(name, new TagListNode(name, value, list));
                }
                continue;
            }

            PropInfo prop = f.getDeclaredAnnotation(PropInfo.class);
            if (prop != null) {
                String name = prop.name();
                if (name.isEmpty()) {
                    name = f.getName();
                }
                subs.put(name, new PropNode(name, this.obj, f));
                continue;
            }

            if (cont == null) {
                cont = f.getDeclaredAnnotation(ContentInfo.class);
                if (cont != null) {
                    contF = f;
                }
            }
        }

        if (inline != null) {
            inline.read(xmlReader);
        }
        else {
            while (xmlReader.hasNext()) {
                int event = xmlReader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    Node node = subs.get(xmlReader.getLocalName());
                    if (node == null) {
                        throw new Exception(xmlReader.getLocalName() + " NOT FOUND in " + this.name);
                    }
                    node.read(xmlReader);
                }
                else if (event == XMLEvent.CHARACTERS) {
                    if (cont != null) {
                        contF.set(this.obj, XObject.read(contF, xmlReader.getText()));
                    }
                }
                else if (event == XMLEvent.CDATA) {
                    if (cont != null) {
                        contF.set(this.obj, XObject.read(contF, xmlReader.getText()));
                    }
                }
                else if (event == XMLEvent.END_ELEMENT) {
                    break;
                }
            }
        }
        return this.obj;

    }

    @Override
    public String toString() {
        return this.name;
    }

}
