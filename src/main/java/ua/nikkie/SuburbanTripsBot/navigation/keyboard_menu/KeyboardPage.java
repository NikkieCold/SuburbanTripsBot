package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.*;

public enum KeyboardPage {

    INLINE_KEYBOARD_MESSAGE {
        @Override
        public String getText() {
            return null;
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return null;
        }
    },

    START_MENU {
        @Override
        public String getText() {
            return "Вітаю! Вкажи хто ти:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(START_DRIVER, START_PASSENGER)
                    .addRow(START_CONTACT)
                    .addRow(START_INFO)
                    .build();
        }
    },

    DRIVER_MENU {
        @Override
        public String getText() {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(DRIVER_ACTIVE)
                    .addRow(DRIVER_CREATE, DRIVER_TRIPS)
                    .addRow(DRIVER_PROFILE)
                    .addRow(DRIVER_TO_PASSENGER)
                    .build();
        }
    },

    DRIVER_NAME_SPECIFYING {
        @Override
        public String getText() {
            return "Для початку роботи введи ім'я, яке буде видно іншим користувачам:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_PHONE_NUMBER_SPECIFYING {
        @Override
        public String getText() {
            return "Напиши актуальний номер телефону для зв'язку";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_CAR_MODEL_SPECIFYING {
        @Override
        public String getText() {
            return "Напиши марку та модель свого авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_SEATS_NUMBER_SPECIFYING {
        @Override
        public String getText() {
            return "Вкажи кількість вільних місць у авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_CAR_PHOTO_SPECIFYING {
        @Override
        public String getText() {
            return "Відправ фото свого автомобіля, яке буде відображится пасажирам:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_PROFILE_MENU {
        //TODO
        @Override
        public String getText() {
            return "TEST";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    PASSENGER_MENU {
        @Override
        public String getText() {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                    .addRow(PASSENGER_ACTIVE)
                    .addRow(PASSENGER_CREATE, PASSENGER_REQUESTS)
                    .addRow(PASSENGER_PROFILE)
                    .addRow(PASSENGER_TO_DRIVER)
                    .build();
        }
    };

    private static final ReplyKeyboard REPLY_KEYBOARD_REMOVE = new ReplyKeyboardRemove(true);

    public SendMessage getResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(getText())
                .build();
    }

    public SendMessage getResponseWithCustomText(Message message, String text) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(text)
                .build();
    }

    public abstract String getText();

    public abstract ReplyKeyboard getReplyMarkup();
}
