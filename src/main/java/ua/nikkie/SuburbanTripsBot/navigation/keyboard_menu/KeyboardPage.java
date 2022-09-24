package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.*;

public enum KeyboardPage {

    INLINE_KEYBOARD_MESSAGE {
        @Override
        public String getText(Message message) {
            return null;
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return null;
        }
    },

    START_MENU {
        @Override
        public String getText(Message message) {
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
        public String getText(Message message) {
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
        public String getText(Message message) {
            return "Для початку роботи введи ім'я, яке буде видно іншим користувачам:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_PHONE_NUMBER_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Напиши актуальний номер телефону для зв'язку";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_CAR_MODEL_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Напиши марку та модель свого авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_SEATS_NUMBER_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Вкажи кількість вільних місць у авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    DRIVER_CAR_PHOTO_SPECIFYING {
        @Override
        public String getText(Message message) {
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
        public String getText(Message message) {
            return botUserService.getBotUser(message).getName();
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return REPLY_KEYBOARD_REMOVE;
        }
    },

    PASSENGER_MENU {
        @Override
        public String getText(Message message) {
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

    public BotUserService botUserService;

    public SendMessage getResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(getText(message))
                .build();
    }

    public SendMessage getResponseWithCustomText(Message message, String text) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(text)
                .build();
    }

    private void setBotUserService(BotUserService botUserService) {
        this.botUserService = botUserService;
    }

    public abstract String getText(Message message);

    public abstract ReplyKeyboard getReplyMarkup();

    @Component
    private static class BotUserServiceComponent {
        @Autowired
        private BotUserService botUserService;

        @PostConstruct
        public void postConstruct() {
            for (KeyboardPage kp : KeyboardPage.values()) {
                kp.setBotUserService(botUserService);
            }
        }
    }
}
