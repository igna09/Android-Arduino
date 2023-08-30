package com.example.carduino.shared.models.carstatus;

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

        public Time() {
        }
    }

    public Duration(Integer hours, Integer minutes, Integer seconds) {
        super();
        setValue(new Time(hours, minutes, seconds));
    }

    public Duration() {
        super();
        setValue(new Time());
    }
}
