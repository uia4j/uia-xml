package uia.xml.model1;

import java.util.Date;

import uia.xml.AttrInfo;
import uia.xml.ContentInfo;
import uia.xml.DateAttrInfo;
import uia.xml.TagInfo;

@TagInfo(name = "salary")
public class Salary {

    @AttrInfo(name = "currency")
    public String currency;

    @DateAttrInfo(name = "updated", format = "yyyy-MM-dd")
    public Date updated;

    @ContentInfo
    public int pay;

    public Salary() {
        this.updated = new Date();
    }

    @Override
    public String toString() {
        return this.currency + "=" + this.pay;
    }
}
