package com.example.carduino.settings.factory;

public enum SettingsEnum {
    BOOLEAN("BOOLEAN", Boolean.class),
    INTEGER("INTEGER", Integer.class),
    STRING("STRING", Integer.class),
    FLOAT("FLOAT", Float.class);

    private String name;
    private Class clazz;

    SettingsEnum(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
