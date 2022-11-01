package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_CAR_MODEL_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_CAR_PHOTO_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_NAME_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PHONE_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_SEATS_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.INLINE_KEYBOARD_MESSAGE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.PASSENGER_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.START_MENU;

public enum KeyboardButton {

    START(
            START_MENU, "/start"),

    START_DRIVER(
            DRIVER_MENU, "Я - водій \uD83D\uDE97"),
    START_PASSENGER(
            PASSENGER_MENU, "Я - пасажир \uD83D\uDEB6"),
    START_CONTACT(
            INLINE_KEYBOARD_MESSAGE, "Зв'язок з розробником"),
    START_INFO(
            INLINE_KEYBOARD_MESSAGE, "Інформація про бота"),

    DRIVER_ACTIVE(
            null, "\uD83D\uDCCB Розміщені оголошення від пасажирів"),
    DRIVER_CREATE(
            null, "\u270D Створити поїздку"),
    DRIVER_TRIPS(
            null, "\uD83D\uDCAC Мої поїздки"),
    DRIVER_PROFILE(
            DRIVER_PROFILE_MENU, "Мій профіль водія"),

    DRIVER_PROFILE_EDIT_NAME(
        DRIVER_NAME_SPECIFYING, "Змінити ім'я"),
    DRIVER_PROFILE_EDIT_PHONE_NUMBER(
        DRIVER_PHONE_NUMBER_SPECIFYING, "Змінити номер телефону"),
    DRIVER_PROFILE_EDIT_CAR_MODEL(
        DRIVER_CAR_MODEL_SPECIFYING, "Змінити марку та модель авто"),
    DRIVER_PROFILE_EDIT_SEATS_NUMBER(
        DRIVER_SEATS_NUMBER_SPECIFYING, "Змінити кількість вільних місць"),
    DRIVER_PROFILE_EDIT_CAR_PHOTO(
        DRIVER_CAR_PHOTO_SPECIFYING, "Змінити фото автомобіля"),
    DRIVER_PROFILE_BACK(
        DRIVER_MENU, "\u2B05 Назад (меню водія)"),

    DRIVER_TO_PASSENGER(
            PASSENGER_MENU, "Режим пасажира"),

    PASSENGER_ACTIVE(
            null, "\uD83D\uDCCB Розміщені поїздки від водіїв"),
    PASSENGER_CREATE(
            null, "\u270D Створити оголошення"),
    PASSENGER_REQUESTS(
            null, "\uD83D\uDCAC Мої оголошення"),
    PASSENGER_PROFILE(
            null, "Мій профіль пасажира"),
    PASSENGER_TO_DRIVER(
            DRIVER_MENU, "Режим водія");

    private final KeyboardPage targetPage;
    private final String buttonText;

    KeyboardButton(KeyboardPage targetPage, String buttonText) {
        this.targetPage = targetPage;
        this.buttonText = buttonText;
    }

    public KeyboardPage getTargetPage() {
        return targetPage;
    }

    public String getButtonText() {
        return buttonText;
    }
}
