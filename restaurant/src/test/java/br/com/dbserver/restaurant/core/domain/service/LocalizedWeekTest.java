package br.com.dbserver.restaurant.core.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.dbserver.restaurant.core.domain.LocalizedWeek;

class LocalizedWeekTest {

    @ParameterizedTest
    @MethodSource("daysOfWeek")
    void getStartOfWeek(LocalDate dayOfWeek) {
        LocalizedWeek localizedWeek = new LocalizedWeek(Locale.forLanguageTag("pt_BR"));
        LocalDate startOfWeek = LocalDate.of(2020, 9, 20);
        assertThat(localizedWeek.getFirstDay(dayOfWeek))
                .isEqualTo(startOfWeek);
    }

    @ParameterizedTest
    @MethodSource("daysOfWeek")
    void getEndOfWeek(LocalDate dayOfWeek) {
        LocalizedWeek localizedWeek = new LocalizedWeek(Locale.forLanguageTag("pt_BR"));
        LocalDate startOfWeek = LocalDate.of(2020, 9, 26);
        assertThat(localizedWeek.getLastDay(dayOfWeek))
                .isEqualTo(startOfWeek);
    }

    private static Stream<Arguments> daysOfWeek() {
        return Stream.of(
                Arguments.of(LocalDate.of(2020, 9, 20)),
                Arguments.of(LocalDate.of(2020, 9, 21)),
                Arguments.of(LocalDate.of(2020, 9, 22)),
                Arguments.of(LocalDate.of(2020, 9, 23)),
                Arguments.of(LocalDate.of(2020, 9, 24)),
                Arguments.of(LocalDate.of(2020, 9, 25)),
                Arguments.of(LocalDate.of(2020, 9, 26))
        );
    }
}
