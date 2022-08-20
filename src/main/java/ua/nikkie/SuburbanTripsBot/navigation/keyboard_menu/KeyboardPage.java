package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.*;

public enum KeyboardPage {
    INLINE_KEYBOARD_MESSAGE {
        @Override
        String getText(Message message) {
            return "INLINE_KEYBOARD_MESSAGE";
        }

        @Override
        ReplyKeyboard getReplyMarkup(Message message) {
            return null;
        }
    },

    NOT_COMMAND {
        @Override
        String getText(Message message) {
            return "Обери команду з меню! \nАбо напиши /start";
        }

        @Override
        ReplyKeyboard getReplyMarkup(Message message) {
            return null;
        }
    },

    START_MENU {
        @Override
        String getText(Message message) {
            return "Вітаю! Вкажи хто ти:";
        }

        @Override
        ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                    .addRow(START_DRIVER, START_PASSENGER)
                    .addRow(START_CONTACT)
                    .addRow(START_INFO)
                    .build();
        }
    },

    DRIVER_MENU {
        @Override
        String getText(Message message) {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        ReplyKeyboard getReplyMarkup(Message message) {
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
        String getText(Message message) {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        ReplyKeyboard getReplyMarkup(Message message) {
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
                .replyMarkup(getReplyMarkup(message))
                .text(getText(message))
                .build();
    }

    abstract String getText(Message message);

    abstract ReplyKeyboard getReplyMarkup(Message message);
}
