package uia.xml.model1;

import uia.xml.PropInfo;
import uia.xml.TagInfo;

@TagInfo(name = "staff")
public class Staff extends Staff0 {

    @PropInfo(name = "name")
    public String name;

    @PropInfo(name = "role")
    public String role;

    @TagInfo
    public Salary salary;

    @PropInfo(name = "bio", cdata = true)
    public String bio;

    public Staff() {
    }
}
