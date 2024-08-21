package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {

    @MethodSource("argumentsStream")
    @ParameterizedTest
    void send_CheckingLanguage(String ip, Location location, Country country, String expected) {

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country))
                .thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String send = messageSender.send(headers);

        Assertions.assertEquals(expected, send);
    }

    public static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, null, 0), Country.RUSSIA, "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, null, 0), Country.USA, "Welcome"),
                Arguments.of("172.", new Location("Moscow", Country.RUSSIA, null, 0), Country.RUSSIA, "Добро пожаловать"));
    }
}
