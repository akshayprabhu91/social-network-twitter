/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

import java.sql.Blob;

/**
 *
 * @author PrabhuA6510
 */
public class CommonObject {

    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    //attribute for the image
    private Blob field6;

    public CommonObject(String f1, String f2, String f3, String f4, String f5) {
        this.field1 = f1;
        this.field2 = f2;
        this.field3 = f3;
        this.field4 = f4;
        this.field5 = f5;
    }

    public CommonObject(String f1, Blob f6, String f3, String f4, String f5) {
        this.field1 = f1;
        this.field6 = f6;
        this.field3 = f3;
        this.field4 = f4;
        this.field5 = f5;
    }

    public CommonObject(String f1, String f2, String f3) {
        this.field1 = f1;
        this.field2 = f2;
        this.field3 = f3;
    }

    public CommonObject(String f1, String f2) {
        this.field1 = f1;
        this.field2 = f2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public Blob getField6() {
        return field6;
    }

    public void setField6(Blob field6) {
        this.field6 = field6;
    }
}
