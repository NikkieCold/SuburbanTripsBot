package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_MY_TRIPS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_CAR_MODEL_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_CAR_PHOTO_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_NAME_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_COMMENT_QUESTION;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_COMMENT_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_DATE_CHOOSING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_DESTINATION_CHOOSING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_SEATS_NUMBER_QUESTION;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_SEATS_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_STOPS_THROUGH_SPECIFYING;
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

    //region Driver BUTTONS
    //region Driver Main Manu
    DRIVER_ACTIVE(
        null, "\uD83D\uDCCB Розміщені оголошення від пасажирів"),
    DRIVER_CREATE(
        DRIVER_TRIP_DESTINATION_CHOOSING, "\u270D Створити поїздку"),
    DRIVER_MY_TRIPS_BUTTON(
        DRIVER_MY_TRIPS, "\uD83D\uDCAC Мої поїздки"),
    DRIVER_PROFILE(
        DRIVER_PROFILE_MENU, "Мій профіль водія"),
    DRIVER_TO_PASSENGER(
        PASSENGER_MENU, "Режим пасажира"),

    DRIVER_BACK_TO_MAIN_MENU(
        DRIVER_MENU, "\u2B05 Назад (меню водія)"),
    //endregion

    //region Driver Profile
    DRIVER_PROFILE_EDIT_NAME(
        DRIVER_PROFILE_NAME_SPECIFYING, "Змінити ім'я"),
    DRIVER_PROFILE_EDIT_PHONE_NUMBER(
        DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING, "Змінити номер телефону"),
    DRIVER_PROFILE_EDIT_CAR_MODEL(
        DRIVER_PROFILE_CAR_MODEL_SPECIFYING, "Змінити марку та модель авто"),
    DRIVER_PROFILE_EDIT_SEATS_NUMBER(
        DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING, "Змінити кількість вільних місць"),
    DRIVER_PROFILE_EDIT_CAR_PHOTO(
        DRIVER_PROFILE_CAR_PHOTO_SPECIFYING, "Змінити фото автомобіля"),
    //endregion

    //region Driver Trip
    DRIVER_TRIP_CREATE_DESTINATION_TO_VASYLKIV(
        null, "Київ -> Васильків"
    ),
    DRIVER_TRIP_CREATE_DESTINATION_TO_KYIV(
        null, "Васильків -> Київ"
    ),
    DRIVER_TRIP_CREATE_STOP_FROM_SPECIFYING_CHANGE_DESTINATION(
        DRIVER_TRIP_DESTINATION_CHOOSING, "Змінити напрямок"
    ),
    DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_YES(
        DRIVER_TRIP_STOPS_THROUGH_SPECIFYING, "Так, вказати"
    ),
    DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_NO(
        DRIVER_TRIP_COMMENT_QUESTION, "Ні, не вказувати"
    ),
    DRIVER_TRIP_CREATE_COMMENT_QUESTION_YES(
        DRIVER_TRIP_COMMENT_SPECIFYING, "Так, написати"
    ),
    DRIVER_TRIP_CREATE_COMMENT_QUESTION_NO(
        DRIVER_TRIP_SEATS_NUMBER_QUESTION, "Ні, пропустити"
    ),
    DRIVER_TRIP_SEATS_NUMBER_QUESTION_YES(
        DRIVER_TRIP_DATE_CHOOSING, "Так, вказати таке саме значення"
    ),
    DRIVER_TRIP_SEATS_NUMBER_QUESTION_NO(
        DRIVER_TRIP_SEATS_NUMBER_SPECIFYING, "Ні, змінити к-сть місць для поїздки"
    ),
    //endregion
    //endregion

    //region Passenger BUTTONS
    //region Passenger Main Menu
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
    //endregion
    //endregion

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
