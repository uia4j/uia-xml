package uia.xml;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import uia.xml.model.Company;
import uia.xml.model.Staff;

public class XObjectReaderTest {

    @Test
    public void testCase1() throws XMLStreamException {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case1.xml");
        XObjectReader reader = new XObjectReader();

        Company c = new Company();
        reader.run(c, is);

        System.out.println(c.staffs.size());
        for (Staff s : c.staffs) {
            System.out.printf("%s %s %s %s\n", s.id, s.name, s.role, s.salary);
        }
    }
}
