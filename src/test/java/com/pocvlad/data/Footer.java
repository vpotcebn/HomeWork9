package com.pocvlad.data;

public enum Footer {
    PRODUCT("Product"),
    COMPANY("Company"),
    SOCIAL("Social");

    private final String desc;
    Footer (String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
