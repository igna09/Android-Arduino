package com.example.carduino.trip;

import androidx.cardview.widget.CardView;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.trip.transformators.CardTransformator;

import java.beans.PropertyChangeListenerProxy;

class CardModel {
    String title;
    String maxValue;
    String avgValue;
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
    CardView cardView;
    CardTransformator transformator;

    public CardModel(String title, String maxValue, String avgValue, String unit, int row, int column) {
        this.title = title;
        this.maxValue = maxValue;
        this.avgValue = avgValue;
        this.unit = unit;
        this.row = row;
        this.column = column;
        this.columnSpan = 1;
        this.rowSpan = 1;
        this.textSize = 16;

    }

    public CardModel(int row, int column) {
        this("-", null, null, "-", row, column);
    }

    public void setMaxValueWithTransformator(String value) {
        if(this.transformator != null) {
            this.maxValue = this.transformator.transform(value);
        } else {
            if(value != null) {
                this.maxValue = value;
            } else {
                this.maxValue = "-";
            }
        }
    }

    public void setAvgValueWithTransformator(String value) {
        if(this.transformator != null) {
            this.avgValue = this.transformator.transform(value);
        } else {
            if(value != null) {
                this.avgValue = value;
            } else {
                this.avgValue = "-";
            }
        }
    }
}
