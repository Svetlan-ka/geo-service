package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeoServiceImplTest {

    @MethodSource("argumentsStream")
    @ParameterizedTest
    void byIp_CheckingLocation(String ip, Location expected) {  //проверка определения локации

        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp(ip);

        assertThat(location, equalTo(expected));
    }

    public static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.00.1100", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.0.23.101", new Location("New York", Country.USA, null, 0)));
    }

}
