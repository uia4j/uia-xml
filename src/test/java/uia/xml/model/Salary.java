package uia.xml.model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import uia.xml.TagInfo;
import uia.xml.XObjectR;

@TagInfo(name = "salary")
public class Salary implements XObjectR {

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
}
