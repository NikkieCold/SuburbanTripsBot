package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_ACTIVE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_CREATE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TO_PASSENGER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIPS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_ACTIVE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_CREATE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_PROFILE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_REQUESTS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_TO_DRIVER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_CONTACT;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_DRIVER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_INFO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_PASSENGER;

public enum KeyboardPage {
    INLINE_KEYBOARD_MESSAGE {
        @Override
        String getText() {
            return null;
        }

        @Override
        ReplyKeyboard getReplyMarkup() {
            return null;
        }
    },

//    NOT_COMMAND {
//        @Override
//        String getText() {
//            return "Обери команду з меню! \nАбо напиши /start";
//        }
//
//        @Override
//        ReplyKeyboard getReplyMarkup() {
//            return null;
//        }
//    },

    START_MENU {
        @Override
        String getText() {
            return "Вітаю! Вкажи хто ти:";
        }

        @Override
        ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(START_DRIVER, START_PASSENGER)
                    .addRow(START_CONTACT)
                    .addRow(START_INFO)
                    .build();
        }
    },

    DRIVER_MENU {
        @Override
        String getText() {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(DRIVER_ACTIVE)
                    .addRow(DRIVER_CREATE, DRIVER_TRIPS)
                    .addRow(DRIVER_PROFILE)
                    .addRow(DRIVER_TO_PASSENGER)
                    .build();
        }
    },

    PASSENGER_MENU {
        @Override
        String getText() {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(PASSENGER_ACTIVE)
                    .addRow(PASSENGER_CREATE, PASSENGER_REQUESTS)
                    .addRow(PASSENGER_PROFILE)
                    .addRow(PASSENGER_TO_DRIVER)
                    .build();
        }
    };

    public SendMessage getResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(getText())
                .build();
    }

    abstract String getText();

    abstract ReplyKeyboard getReplyMarkup();
}
