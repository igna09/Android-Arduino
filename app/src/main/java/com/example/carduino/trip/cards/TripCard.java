package com.example.carduino.trip.cards;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.cardview.widget.CardView;

import com.example.carduino.shared.Transformator;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.trip.tripvalue.TripValue;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.trip.transformators.CardTransformator;

public abstract class TripCard {
    private CardView cardView;
    private Integer row;
    private Integer column;
    private Integer columnSpan;
    private Integer rowSpan;
    private Integer textSize;
    private Integer width;
    private Integer height;
    private Integer margin;
    private TripValueEnum tripValueEnum;
    private String title;
    private CardTransformator valueTransformator;

    public TripCard() {
        row = 0;
        column = 0;
        columnSpan = 1;
        rowSpan = 1;
    }

    public GridLayout.LayoutParams getLayoutParams() {
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();

        lp.setGravity(Gravity.CENTER);
        lp.columnSpec = GridLayout.spec(this.getColumn(), this.getColumnSpan());
        lp.rowSpec = GridLayout.spec(this.getRow(), this.getRowSpan());
        lp.height = this.getHeight() != null ? this.getHeight() * this.getRowSpan() : GridLayout.LayoutParams.WRAP_CONTENT;
        lp.width = this.getWidth() != null ? this.getWidth() * this.getColumnSpan() : GridLayout.LayoutParams.WRAP_CONTENT;
        lp.setMargins(margin, margin, margin, margin);

        return lp;
    }

    public void init() {};
    public void updateCard(TripValue value){};
    public abstract void createCard(Context context);

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(Integer columnSpan) {
        this.columnSpan = columnSpan;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public TripValueEnum getTripValueEnum() {
        return tripValueEnum;
    }

    public void setTripValueEnum(TripValueEnum tripValueEnum) {
        this.tripValueEnum = tripValueEnum;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public static String getSafeValue(String value) {
        return value != null && !value.isEmpty() ? value : "-";
    }

    public static Boolean isSafeValue(String value) {
        return value != null && !value.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Transformator getValueTransformator() {
        return valueTransformator;
    }

    public void setValueTransformator(CardTransformator valueTransformator) {
        this.valueTransformator = valueTransformator;
    }

    public String getTransformedValue(String value) {
        if(isSafeValue(value)) {
            if(this.valueTransformator != null) {
                return this.valueTransformator.transform(value);
            } else {
                return value;
            }
        } else {
            return "-";
        }
    }
}
