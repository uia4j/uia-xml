package uia.xml.model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import uia.xml.TagInfo;
import uia.xml.XObjectR;
import uia.xml.XObjectW;

@TagInfo(name = "salary")
public class Salary implements XObjectR, XObjectW {

    private final Staff staff;

    public String currency;

    public int pay;

    public Salary(Staff staff) {
        this.staff = staff;
    }

    @Override
    public XObjectR start(XMLStreamReader reader) throws XMLStreamException {
        this.currency = reader.getAttributeValue(null, "currency");
        reader.next();
        this.pay = Integer.parseInt(reader.getText());
        return this;
    }

    @Override
    public XObjectR end(XMLStreamReader reader) throws XMLStreamException {
        return this.staff;
    }

    @Override
    public String toString() {
        return this.currency + "=" + this.pay;
    }

    @Override
    public void write(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("salary");
        writer.writeAttribute("currency", this.currency);
        writer.writeCharacters("" + this.pay);
        writer.writeEndElement();
    }
}
