package br.com.dbserver.restaurant.core.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class LocalizedWeek {
    private final Locale locale;
    private final DayOfWeek firstDayOfWeek;
    private final DayOfWeek lastDayOfWeek;

    public LocalizedWeek(final Locale locale) {
        this.locale = locale;
        this.firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        this.lastDayOfWeek = DayOfWeek.of(((this.firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    }

    public LocalDate getFirstDay(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek));
    }

    public LocalDate getLastDay(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek));
    }
}
