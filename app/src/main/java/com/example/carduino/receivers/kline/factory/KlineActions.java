package com.example.carduino.receivers.kline.factory;

public enum KlineActions {
    STUB(StubAction.class, "STUB");

    private Class clazz;
    private String name;

    KlineActions(Class clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
