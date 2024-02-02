package uia.xml;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import uia.xml.model1.Company;
import uia.xml.model1.Staff;
import uia.xml.model2.File;
import uia.xml.model2.Picture;
import uia.xml.model2.Program;
import uia.xml.model3.Simple;

public class XObjectReaderTest {

    @Test
    public void testCase1() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case1.xml");
        Company obj = XObjectReader.run(Company.class, is);
        Staff staff = obj.staffs.get(0);
        Assert.assertEquals(obj.staffs.size(), 2);
        Assert.assertEquals(staff.id.intValue(), 1001);
        Assert.assertEquals(staff.name, "Kyle");
        Assert.assertEquals(staff.bio, "HTML tag <code>integration</code>");
        Assert.assertEquals(staff.salary.currency, "USD");
        Assert.assertEquals(staff.salary.pay, 5000);
    }

    @Test
    public void testCase2() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case2.xml");
        Program obj = XObjectReader.run(Program.class, is);
        Picture p = (Picture) obj.panels.get(0);
        File f = (File) obj.panels.get(5);
        Assert.assertEquals(obj.panels.size(), 6);
        Assert.assertEquals(p.getPicUnits().size(), 62);
        Assert.assertEquals(p.getPicUnits().get(0).getX(), 0);
        Assert.assertEquals(p.getPicUnits().get(0).getY(), 1);
        Assert.assertEquals(p.getPicUnits().get(0).getWidth(), 2);
        Assert.assertEquals(p.getPicUnits().get(0).getHeight(), 3);
        Assert.assertEquals(p.getPicUnits().get(1).getX(), 4);
        Assert.assertEquals(p.getPicUnits().get(1).getY(), 5);
        Assert.assertEquals(p.getPicUnits().get(1).getWidth(), 6);
        Assert.assertEquals(p.getPicUnits().get(1).getHeight(), 7);
        Assert.assertEquals(f.getPath(), "c:\\abc\\def\\xyz.pbg");
        Assert.assertEquals(f.getId(), "f1");
    }

    @Test
    public void testCase3() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case3.xml");
        Simple obj = XObjectReader.run(Simple.class, is);
        Assert.assertEquals(obj.elem4s.size(), 3);
        Assert.assertEquals(obj.elem5, "<Elem2 id=\"51\"><Value1>N</Value1><Value2>value512</Value2></Elem2><Elem2 id=\"52\"><Value1>Y</Value1><Value2>value522</Value2></Elem2>");
    }
}
