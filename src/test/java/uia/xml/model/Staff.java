package uia.xml.model;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import uia.xml.TagInfo;
import uia.xml.XObjectR;
import uia.xml.XObjectW;

@TagInfo(name = "staff")
public class Staff implements XObjectR {

    private final Company company;

    public String id;

    public String name;

    public String role;

    public Salary salary;

    public String bio;

    public Staff(Company company) {
        this.company = company;
    }

    @Override
    public XObjectR start(XMLStreamReader reader) throws XMLStreamException {
        this.id = reader.getAttributeValue(null, "id");
        return this;
    }

    @Override
    public XObjectR end(XMLStreamReader reader) throws XMLStreamException {
        return this.company;
    }

    @Override
    public XObjectR subStart(XMLStreamReader reader, String name) throws XMLStreamException {
        if (name.equals("salary")) {
            this.salary = new Salary(this);
            return this.salary;
        }

        if (name.equals("name")) {
            reader.next();
            this.name = reader.getText();
        }
        else if (name.equals("role")) {
            reader.next();
            this.role = reader.getText();
        }
        return this;
    }
}
