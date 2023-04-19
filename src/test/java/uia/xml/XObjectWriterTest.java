package uia.xml;

import org.junit.Test;

import uia.xml.model1.Company;
import uia.xml.model2.Program;
import uia.xml.model3.Simple;

public class XObjectWriterTest {

    @Test
    public void testCase1() throws Exception {
        Company c = XObjectReader.run(Company.class, XObjectWriterTest.class.getResourceAsStream("case1.xml"));
        XObjectWriter.run(c, System.out);
    }

    @Test
    public void testCase2() throws Exception {
        Program p = XObjectReader.run(Program.class, XObjectWriterTest.class.getResourceAsStream("case2.xml"));
        XObjectWriter.run(p, System.out);
    }

    @Test
    public void testCase3() throws Exception {
        Simple s = XObjectReader.run(Simple.class, XObjectWriterTest.class.getResourceAsStream("case3.xml"));
        XObjectWriter.run(s, System.out);
    }
}
