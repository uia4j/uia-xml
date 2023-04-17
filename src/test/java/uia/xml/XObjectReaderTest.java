package uia.xml;

import java.io.InputStream;

import org.junit.Test;

import uia.xml.model1.Company;
import uia.xml.model2.Program;
import uia.xml.model3.Simple;

public class XObjectReaderTest {

    @Test
    public void testCase1() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case1.xml");
        Object obj = XObjectReader.run(Company.class, is);
        System.out.println(obj);
    }

    @Test
    public void testCase2() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case2.xml");
        Program obj = XObjectReader.run(Program.class, is);
        System.out.println(obj);
    }

    @Test
    public void testCase3() throws Exception {
        InputStream is = XObjectReaderTest.class.getResourceAsStream("case3.xml");
        Simple obj = XObjectReader.run(Simple.class, is);
        System.out.println(obj);
    }
}
