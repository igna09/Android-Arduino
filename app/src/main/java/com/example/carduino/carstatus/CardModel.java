package com.example.carduino.carstatus;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;

import java.beans.PropertyChangeListenerProxy;

class CardModel {
    String title;
    String value;
    String unit;
    Integer row;
    Integer column;
    Integer columnSpan;
    Integer rowSpan;
    Integer textSize;
    Integer width;
    Integer height;
    CarStatusEnum carstatusEnum;
    PropertyChangeListenerProxy propertyChangeListener;

    public CardModel(String title, String value, String unit, int row, int column) {
        this.title = title;
        this.value = value;
        this.unit = unit;
        this.row = row;
        this.column = column;
        this.columnSpan = 1;
        this.rowSpan = 1;
        this.textSize = 16;
    }

    public CardModel(int row, int column) {
        this.title = "-";
        this.value = "-";
        this.unit = "-";
        this.row = row;
        this.column = column;
        this.columnSpan = 1;
        this.rowSpan = 1;
        this.textSize = 16;
    }
}
