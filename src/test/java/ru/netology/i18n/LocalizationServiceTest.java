package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

public class LocalizationServiceTest {

    LocalizationService localizationService;

    @BeforeEach
    public void createNewLocalizationService() {
        localizationService = new LocalizationServiceImpl();
    }

    @Test
    public void shouldGiveMessageInRussian() {
        String actual = localizationService.locale(Country.RUSSIA);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldGiveMessageInEnglish() {
        String actual = localizationService.locale(Country.USA);
        String expected = "Welcome";
        Assertions.assertEquals(expected, actual);
    }
}

