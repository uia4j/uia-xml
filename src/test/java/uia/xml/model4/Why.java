package uia.xml.model4;

import java.util.List;

import uia.xml.PropInfo;

public class Why {

    @PropInfo(name = "A")
    public String a;

    @PropInfo(name = "B")
    public String b;

    @PropInfo(name = "C", multi = true)
    public List<String> cs;
}
