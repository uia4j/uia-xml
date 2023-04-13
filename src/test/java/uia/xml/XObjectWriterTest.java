package uia.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.Test;

import uia.xml.model.Company;

public class XObjectWriterTest {

    @Test
    public void testCase2() throws Exception {
        InputStream is = XObjectWriterTest.class.getResourceAsStream("case1.xml");
        XObjectReader reader = new XObjectReader();

        Company c = new Company();
        reader.run(c, is);

        XObjectWriter writer = new XObjectWriter();
        FileOutputStream fos = new FileOutputStream(new File("d:/temp/case2.xml"));
        writer.run(c, fos);
        fos.flush();
        fos.close();

    }
}
