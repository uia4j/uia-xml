package uia.xml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import uia.xml.TagInfo;
import uia.xml.XObjectR;

@TagInfo(name = "Company")
public class Company implements XObjectR {

    public final List<Staff> staffs;

    public Company() {
        this.staffs = new ArrayList<>();
    }

    @Override
    public XObjectR subStart(XMLStreamReader reader, String name) throws XMLStreamException {
        if (name.equals("staff")) {
            Staff staff = new Staff(this);
            this.staffs.add(staff);
            return staff;
        }
        return this;
    }
}
