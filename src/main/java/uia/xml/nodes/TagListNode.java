package uia.xml.nodes;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.PropInfo;
import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

public class TagListNode implements Node {

    private final String name;

    private final List<Object> objs;

    private Map<String, Class<?>> mapping;

    public TagListNode(String name, List<Object> objs, TagListInfo info) {
        this.name = name;
        this.objs = objs;
        this.mapping = new TreeMap<String, Class<?>>();
        for (TagListElem elem : info.elems()) {
            for (Annotation an : elem.type().getDeclaredAnnotations()) {
                if (an instanceof TagInfo) {
                    TagInfo tag = (TagInfo) an;
                    this.mapping.put(tag.name(), elem.type());
                }
                else if (an instanceof TagListInfo) {
                    TagListInfo list = (TagListInfo) an;
                    this.mapping.put(list.name(), elem.type());
                }
                else if (an instanceof PropInfo) {
                    PropInfo prop = (PropInfo) an;
                    this.mapping.put(prop.name(), elem.type());
                }
            }
        }
    }

    @Override
    public List<Object> read(XMLStreamReader xmlReader) throws Exception {
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                Class<?> clz = this.mapping.get(xmlReader.getLocalName());
                if (clz == null) {
                    throw new Exception(xmlReader.getLocalName() + " NOT FOUND in " + this.name);
                }
                Object value = clz.newInstance();
                for (Annotation an : clz.getDeclaredAnnotations()) {
                    Node node = null;
                    if (an instanceof TagInfo) {
                        node = new TagNode(xmlReader.getLocalName(), value);
                    }
                    else if (an instanceof TagListInfo) {
                        node = new TagListNode(xmlReader.getLocalName(), new ArrayList<Object>(), (TagListInfo) an);
                    }
                    else if (an instanceof PropInfo) {
                        node = new PropNode(xmlReader.getLocalName());
                    }
                    else {
                        continue;
                    }
                    this.objs.add(node.read(xmlReader));
                }
            }
            else if (event == XMLEvent.END_ELEMENT) {
                break;
            }
        }
        return this.objs;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
