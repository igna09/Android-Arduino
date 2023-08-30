package com.example.carduino.shared.models.carstatus.values;

public class Duration extends Value<Duration.Time> {
    public class Time {
        public Integer hours;
        public Integer minutes;
        public Integer seconds;

        public Time(Integer hours, Integer minutes, Integer seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public Time(Integer totalSecs) {
            this.hours = totalSecs/3600;
            this.minutes = (totalSecs % 3600) / 60;
            this.seconds = totalSecs % 60;
        }

        public Time() {
        }
    }

    public Duration(Integer hours, Integer minutes, Integer seconds) {
        super();
        setValue(new Time(hours, minutes, seconds));
    }

    public Duration(Integer seconds) {
        super();
        setValue(new Time(seconds));
    }

    public Duration() {
        super();
        setValue(new Time());
    }

    @Override
    public Time parseValueFromString(String value) {
        Integer totalSecs = Integer.parseInt(value);
        return new Time(totalSecs);
    }
}
