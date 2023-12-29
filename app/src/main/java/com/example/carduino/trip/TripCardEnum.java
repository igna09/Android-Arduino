package com.example.carduino.trip;

import com.example.carduino.shared.Transformator;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.trip.cards.AvgMaxTripCard;
import com.example.carduino.trip.cards.ButtonsTripCard;
import com.example.carduino.trip.cards.DistanceTripCard;
import com.example.carduino.trip.transformators.CardTransformator;
import com.example.carduino.trip.transformators.FloatTransformator;

public enum TripCardEnum {
    SPEED("SPEED", CarStatusEnum.SPEED, 0, 0, AvgMaxTripCard.class, new FloatTransformator("0.0")),
    FUEL_CONSUMPTION("FUEL CONSUMPTION", CarStatusEnum.FUEL_CONSUMPTION, 0, 1, AvgMaxTripCard.class, new FloatTransformator("0.0")),
    DISTANCE("DISTANCE", CarStatusEnum.DISTANCE, 0, 2, DistanceTripCard.class, new FloatTransformator("0.0")),
    BUTTONS(null, null, 0, 3, ButtonsTripCard.class, null);

    String label;
    Integer row;
    Integer column;
    CarStatusEnum carstatusEnum;
    Class cardTripClass;
    CardTransformator transformator;

    TripCardEnum(String label, CarStatusEnum carstatusEnum, Integer row, Integer column, Class cardTripClass, CardTransformator transformator) {
        this.label = label;
        this.row = row;
        this.column = column;
        this.carstatusEnum = carstatusEnum;
        this.cardTripClass = cardTripClass;
        this.transformator = transformator;
    }
}
