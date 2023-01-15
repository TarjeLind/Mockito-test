package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceTest {

    public static final String MOSCOW_IP = "172.0.32.12";
    public static final String NEW_YORK_IP = "96.44.183.148";

    GeoService geoService;

    @BeforeEach
    public void createGeoService() {
        geoService = new GeoServiceImpl();
    }

    @Test
    public void shouldReturnRussianLocation() {
        Location actualLocation = geoService.byIp(MOSCOW_IP);
        Location expectedLocation = new Location("Moscow", Country.RUSSIA, null, 0);
        Assertions.assertEquals(expectedLocation.getCountry(), actualLocation.getCountry());
        Assertions.assertEquals(expectedLocation.getCity(), actualLocation.getCity());
    }

    @Test
    public void shouldReturnUSALocation() {
        Location actualLocation = geoService.byIp(NEW_YORK_IP);
        Location expectedLocation = new Location("New York", Country.USA, null,  0);
        Assertions.assertEquals(expectedLocation.getCountry(), actualLocation.getCountry());
        Assertions.assertEquals(expectedLocation.getCity(), actualLocation.getCity());
    }
}
