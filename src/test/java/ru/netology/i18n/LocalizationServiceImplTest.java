package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocalizationServiceImplTest {

    @MethodSource("argumentsStream")
    @ParameterizedTest
    void locale_CheckingMessage(Country country, String expected) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String message = localizationService.locale(country);
        assertThat(message, equalTo(expected));
    }

    public static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"));
    }
}
