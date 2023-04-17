package uia.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import uia.xml.nodes.TagNode;

public final class XObjectReader {

    public static <T> T run(Class<T> clz, File file) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return run(clz, fis);
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, String xmlContent) throws Exception {
        InputStream bais = null;
        try {
            bais = new ByteArrayInputStream(xmlContent.getBytes());
            return run(clz, bais);
        }
        finally {
            if (bais != null) {
                bais.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, String xmlContent, String charsetName) throws Exception {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(xmlContent.getBytes(charsetName));
            return run(clz, bais);
        }
        finally {
            if (bais != null) {
                bais.close();
            }
        }
    }

    public static <T> T run(Class<T> clz, InputStream fis) throws Exception {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        // https://rules.sonarsource.com/java/RSPEC-2755
        // prevent xxe
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        T t = clz.newInstance();
        XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(fis);
        while (xmlReader.hasNext()) {
            int event = xmlReader.next();
            if (event == XMLEvent.START_ELEMENT) {
                TagInfo tag = clz.getDeclaredAnnotation(TagInfo.class);
                if (tag != null) {
                    new TagNode(xmlReader.getLocalName(), t).read(xmlReader);
                }
                break;
            }
        }
        return t;
    }
}
