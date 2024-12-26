package uia.xml.model1;

import java.util.List;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo(name = "Company")
public class Company {

    @TagListInfo(
            inline = true,
            elems = { @TagListElem(name = "staff", type = Staff.class) })
    public List<Staff> staffs;
}
