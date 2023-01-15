package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {

    public static final String MOSCOW_IP = "172.0.32.11";
    public static final String RUSSIAN_IP = "172.4.42.11";
    public static final String NEW_YORK_IP = "96.44.183.149";
    public static final String USA_IP = "96.44.153.149";

    MessageSender messageSender;
    GeoService geoService;
    LocalizationService localizationService;
    Map<String, String> headers;

    @BeforeEach
    public void createServicesAndMessageSenderAndHeaders() {
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<String, String>();
    }

    // Проверить, что MessageSenderImpl всегда отправляет только русский текст,
    // если ip относится к российскому сегменту адресов.
    @ParameterizedTest
    @ValueSource(strings = {MOSCOW_IP, RUSSIAN_IP})
    public void shouldReturnRussianTextWhenRussianIp(String ip) {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        Mockito.when(geoService.byIp(ip)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        String actualMessage = messageSender.send(headers);
        String expectedMessage = "Добро пожаловать";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    // Проверить, что MessageSenderImpl всегда отправляет только английский текст,
    // если ip относится к американскому сегменту адресов.
    @ParameterizedTest
    @ValueSource(strings = {NEW_YORK_IP, USA_IP})
    public void shouldReturnEnglishTextWhenUSAIp(String ip) {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        Mockito.when(geoService.byIp(ip)).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String actualMessage = messageSender.send(headers);
        String expectedMessage = "Welcome";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
