package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_ACTIVE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_CREATE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_BACK;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_CAR_MODEL;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_CAR_PHOTO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_NAME;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_PHONE_NUMBER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_SEATS_NUMBER;
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

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;

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

        @Override
        public boolean isUserInputPage() {
            return true;
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

        @Override
        public boolean isUserInputPage() {
            return true;
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

        @Override
        public boolean isUserInputPage() {
            return true;
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

        @Override
        public boolean isUserInputPage() {
            return true;
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

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_PROFILE_MENU {
        @Override
        public String getText(Message message) {
            BotUser user = botUserService.getBotUser(message);
            return "__*Ваш профіль в SuburbanTripsBot*__\n"
                + "\n_Ім'я:_ `" + user.getName()
                + "`\n_Номер телефону:_ `" + user.getPhoneNumber()
                + "`\nМодель авто: `" + user.getCarModel()
                + "`\nКількість вільних місць: `" + user.getSeatsNumber()
                + "`\n\nВи можете змінити дані профілю скориставшись кнопками внизу"
                + "[⠀](https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg)";
        }

        @Override
        public ReplyKeyboard getReplyMarkup() {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_PROFILE_EDIT_NAME, DRIVER_PROFILE_EDIT_PHONE_NUMBER)
                .addRow(DRIVER_PROFILE_EDIT_CAR_MODEL, DRIVER_PROFILE_EDIT_SEATS_NUMBER)
                .addRow(DRIVER_PROFILE_BACK, DRIVER_PROFILE_EDIT_CAR_PHOTO)
                .build();
        }

        @Override
        public PartialBotApiMethod<Message> getResponse(Message message) {
            return SendPhoto.builder()
                .chatId(message.getChatId().toString())
                .photo(new InputFile(botUserService.getBotUser(message).getCarPhoto()))
                .caption(getText(message))
                .replyMarkup(getReplyMarkup())
                .parseMode(MARKDOWN_V2).build();
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

    private static final String MARKDOWN_V2 = "MarkdownV2";
    private static final ReplyKeyboard REPLY_KEYBOARD_REMOVE = new ReplyKeyboardRemove(true);

    public BotUserService botUserService;

    private void setBotUserService(BotUserService botUserService) {
        this.botUserService = botUserService;
    }

    public PartialBotApiMethod<Message> getResponse(Message message) {
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

    public boolean isUserInputPage() {
        return false;
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
