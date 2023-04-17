package uia.xml.model1;

import java.util.ArrayList;

import uia.xml.TagInfo;
import uia.xml.TagListElem;
import uia.xml.TagListInfo;

@TagInfo(name = "Company")
public class Company {

    @TagListInfo(
            inline = true,
            elems = { @TagListElem(name = "staff", type = Staff.class) })
    public final ArrayList<Staff> staffs;

    public Company() {
        this.staffs = new ArrayList<Staff>();
    }
}
