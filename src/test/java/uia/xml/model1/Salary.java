package uia.xml.model1;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.TagInfo;

@TagInfo(name = "salary")
public class Salary {

    @AttrInfo(name = "currency")
    public String currency;

    @ContentInfo
    public int pay;

    public Salary() {
    }

    @Override
    public String toString() {
        return this.currency + "=" + this.pay;
    }
}
