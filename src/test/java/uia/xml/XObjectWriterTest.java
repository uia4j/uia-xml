package uia.xml;

import org.junit.Test;

import uia.xml.model1.Company;
import uia.xml.model2.Program;
import uia.xml.model3.Simple;

public class XObjectWriterTest {

    @Test
    public void testCase1() throws Exception {
        Company c = XObjectReader.run(Company.class, XObjectWriterTest.class.getResourceAsStream("case1.xml"));
        System.out.println(XObjectWriter.run(c, "utf-8"));
    }

    @Test
    public void testCase2() throws Exception {
        Program p = XObjectReader.run(Program.class, XObjectWriterTest.class.getResourceAsStream("case2.xml"));
        System.out.println(XObjectWriter.run(p, "utf-8"));
    }

    @Test
    public void testCase3() throws Exception {
        Simple s = XObjectReader.run(Simple.class, XObjectWriterTest.class.getResourceAsStream("case3.xml"));
        System.out.println(XObjectWriter.run(s, "utf-8"));
    }
}
