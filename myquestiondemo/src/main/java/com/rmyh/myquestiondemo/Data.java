package com.rmyh.myquestiondemo;

/**
 * Created by wen on 2017/7/17.
 */

public class Data {

    private int type;
    private String test;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;

    public Data(int type, String test,boolean isSelect) {
        this.type = type;
        this.test = test;
        this.isSelect = isSelect;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
