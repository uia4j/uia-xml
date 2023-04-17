package uia.xml;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import uia.xml.model1.Company;
import uia.xml.model2.Program;
import uia.xml.model3.Simple;

public class XObjectWriterTest {

    @Test
    public void testCase1() throws Exception {
        Company c = XObjectReader.run(Company.class, XObjectWriterTest.class.getResourceAsStream("case1.xml"));

        XObjectWriter writer = new XObjectWriter();
        FileOutputStream fos = new FileOutputStream(new File("d:/temp/case1_again.xml"));
        writer.run(c, fos);
        fos.flush();
        fos.close();
    }

    @Test
    public void testCase2() throws Exception {
        Program p = XObjectReader.run(Program.class, XObjectWriterTest.class.getResourceAsStream("case2.xml"));

        XObjectWriter writer = new XObjectWriter();
        FileOutputStream fos = new FileOutputStream(new File("d:/temp/case2_again.xml"));
        writer.run(p, fos);
        fos.flush();
        fos.close();
    }

    @Test
    public void testCase3() throws Exception {
        Simple s = XObjectReader.run(Simple.class, XObjectWriterTest.class.getResourceAsStream("case3.xml"));

        XObjectWriter writer = new XObjectWriter();
        FileOutputStream fos = new FileOutputStream(new File("d:/temp/case3_again.xml"));
        writer.run(s, fos);
        fos.flush();
        fos.close();
    }
}
