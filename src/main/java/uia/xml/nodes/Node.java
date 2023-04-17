package uia.xml.nodes;

import javax.xml.stream.XMLStreamReader;

public interface Node {

    public Object read(XMLStreamReader xmlReader) throws Exception;

}
