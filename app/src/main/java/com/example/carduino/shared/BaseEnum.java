package com.example.carduino.shared;

public interface BaseEnum {
    Integer getId();

    static BaseEnum getEnumById(Integer id) {
        return null;
    }

    static BaseEnum getEnumByName(String name) {
        return null;
    }
}
