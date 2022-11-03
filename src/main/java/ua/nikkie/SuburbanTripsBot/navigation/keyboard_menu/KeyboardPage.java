package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_ACTIVE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_BACK_TO_MAIN_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_CREATE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_CAR_MODEL;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_CAR_PHOTO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_NAME;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_PHONE_NUMBER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_PROFILE_EDIT_SEATS_NUMBER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TO_PASSENGER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIPS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_COMMENT_QUESTION_NO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_COMMENT_QUESTION_YES;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_DESTINATION_TO_KYIV;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_DESTINATION_TO_VASYLKIV;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_NO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_YES;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_SEATS_NUMBER_QUESTION_NO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_SEATS_NUMBER_QUESTION_YES;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_ACTIVE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_CREATE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_PROFILE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_REQUESTS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.PASSENGER_TO_DRIVER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_CONTACT;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_DRIVER;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_INFO;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_PASSENGER;
import static ua.nikkie.SuburbanTripsBot.util.BotUtil.DateTime.getDateChoosingKeyboard;
import static ua.nikkie.SuburbanTripsBot.util.BotUtil.DateTime.getTimeChoosingKeyboard;
import static ua.nikkie.SuburbanTripsBot.util.BotUtil.DateTime.getTimeRangeChoosingKeyboard;

import java.util.List;
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
import ua.nikkie.SuburbanTripsBot.entities.enums.DriverTripDestination;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;
import ua.nikkie.SuburbanTripsBot.entities.services.DriverTripService;

public enum KeyboardPage {

    INLINE_KEYBOARD_MESSAGE {
        @Override
        public String getText(Message message) {
            return null;
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return null;
        }
    },

    START_MENU {
        @Override
        public String getText(Message message) {
            return "Вітаю! Вкажи хто ти:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                    .addRow(START_DRIVER, START_PASSENGER)
                    .addRow(START_CONTACT)
                    .addRow(START_INFO)
                    .build();
        }
    },

    //region Driver PAGES
    DRIVER_MENU {
        @Override
        public String getText(Message message) {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                    .addRow(DRIVER_ACTIVE)
                    .addRow(DRIVER_CREATE, DRIVER_TRIPS)
                    .addRow(DRIVER_PROFILE)
                    .addRow(DRIVER_TO_PASSENGER)
                    .build();
        }
    },

    //region Driver Profile

    DRIVER_PROFILE_MENU {
        @Override
        public String getText(Message message) {
            BotUser user = botUserService.getBotUser(message);
            return "__*Ваш профіль в SuburbanTripsBot*__\n"
                + "\n_Ім'я:_ `" + user.getName()
                + "`\n_Номер телефону:_ `" + user.getPhoneNumber()
                + "`\n_Модель авто:_ `" + user.getCarModel()
                + "`\n_Кількість вільних місць:_ `" + user.getSeatsNumber()
                + "`\n\nВи можете змінити дані профілю скориставшись кнопками внизу";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_PROFILE_EDIT_NAME, DRIVER_PROFILE_EDIT_PHONE_NUMBER)
                .addRow(DRIVER_PROFILE_EDIT_CAR_MODEL, DRIVER_PROFILE_EDIT_SEATS_NUMBER)
                .addRow(DRIVER_BACK_TO_MAIN_MENU, DRIVER_PROFILE_EDIT_CAR_PHOTO)
                .build();
        }

        @Override
        public List<PartialBotApiMethod<Message>> getResponse(Message message) {
            return singletonList(SendPhoto.builder()
                .chatId(message.getChatId().toString())
                .photo(new InputFile(botUserService.getBotUser(message).getCarPhoto()))
                .caption(getText(message))
                .replyMarkup(getReplyMarkup(message))
                .parseMode(MARKDOWN_V2).build());
        }
    },
    DRIVER_PROFILE_NAME_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Для початку роботи введи ім'я, яке буде видно іншим користувачам:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Напиши актуальний номер телефону для зв'язку";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_PROFILE_CAR_MODEL_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Напиши марку та модель свого авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Вкажи кількість вільних місць у авто:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_PROFILE_CAR_PHOTO_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Відправ фото свого автомобіля, яке буде відображится пасажирам:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },
    //endregion

    //region Driver Trip
    DRIVER_MY_TRIPS {
        @Override
        public String getText(Message message) {
            return "Trips count: " + driverTripService.getDriverTrips(message).size();
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_BACK_TO_MAIN_MENU)
                .build();
        }
    },

    DRIVER_TRIP_DESTINATION_CHOOSING {
        @Override
        public String getText(Message message) {
            return "Обери напрямок поїздки:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_TRIP_CREATE_DESTINATION_TO_KYIV)
                .addRow(DRIVER_TRIP_CREATE_DESTINATION_TO_VASYLKIV)
                .build();
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_STOP_FROM_SPECIFYING {

        private final static String FROM_SPECIFYING_TEXT_FORMAT = "Вкажи з якої зупинки в %s будеш їхати:";
        @Override
        public String getText(Message message) {
            DriverTripDestination destination = driverTripService.getUnfinishedTrip(message).getDestination();
            return format(FROM_SPECIFYING_TEXT_FORMAT, destination.getFromCity());
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_STOP_TO_SPECIFYING {

        private final static String TO_SPECIFYING_TEXT_FORMAT = "Вкажи до якої зупинки в %s будеш їхати:";

        @Override
        public String getText(Message message) {
            DriverTripDestination destination = driverTripService.getUnfinishedTrip(message).getDestination();
            return format(TO_SPECIFYING_TEXT_FORMAT, destination.getToCity());
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_STOPS_THROUGH_QUESTION {
        @Override
        public String getText(Message message) {
            return "Бажаєш вказати проміжні зупинки?";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_YES)
                .addRow(DRIVER_TRIP_CREATE_STOPS_THROUGH_QUESTION_NO)
                .build();
        }
    },

    DRIVER_TRIP_STOPS_THROUGH_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Вкажи проміжні зупинки, які прожджатимеш через кому:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_COMMENT_QUESTION {
        @Override
        public String getText(Message message) {
            return "Бажаєш вказати додатковий коментар для пасажирів?";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_TRIP_CREATE_COMMENT_QUESTION_YES)
                .addRow(DRIVER_TRIP_CREATE_COMMENT_QUESTION_NO)
                .build();
        }
    },

    DRIVER_TRIP_COMMENT_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Напиши повідомлення яке буде прикрплено до поїздки і показано пасажирам:"
                + "\n(Не більше 150 символів)";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_SEATS_NUMBER_QUESTION {

        private static final String SEATS_NUMBERS_TEXT_FORMAT = "Кількість вільних місць зазначена у профілі: `%s`"
            + "\nВказати таку ж к-сть місць для цієї поїздки?";

        @Override
        public String getText(Message message) {
            return format(SEATS_NUMBERS_TEXT_FORMAT, botUserService.getBotUser(message).getSeatsNumber());
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                .addRow(DRIVER_TRIP_SEATS_NUMBER_QUESTION_YES)
                .addRow(DRIVER_TRIP_SEATS_NUMBER_QUESTION_NO)
                .build();
        }
    },

    DRIVER_TRIP_SEATS_NUMBER_SPECIFYING {
        @Override
        public String getText(Message message) {
            return "Вкажи кількість вільних місць на цю поїздку:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return REPLY_KEYBOARD_REMOVE;
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_DATE_CHOOSING {
        @Override
        public String getText(Message message) {
            return "Обери дату на яку хочеш створити поїздку:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return getDateChoosingKeyboard();
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_TIME_RANGE_CHOOSING {
        @Override
        public String getText(Message message) {
            return "Обери час відправлення:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return getTimeRangeChoosingKeyboard(driverTripService.getUnfinishedTrip(message));
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },

    DRIVER_TRIP_TIME_CHOOSING {
        @Override
        public String getText(Message message) {
            return "Оберіть час відправки";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return getTimeChoosingKeyboard(driverTripService.getUnfinishedTrip(message));
        }

        @Override
        public boolean isUserInputPage() {
            return true;
        }
    },
    //endregion

    //endregion

    //region Passenger PAGES
    PASSENGER_MENU {
        @Override
        public String getText(Message message) {
            return "Ти у головному меню! \nОбери наступну дію:";
        }

        @Override
        public ReplyKeyboard getReplyMarkup(Message message) {
            return ReplyKeyboardBuilder.builder()
                    .addRow(PASSENGER_ACTIVE)
                    .addRow(PASSENGER_CREATE, PASSENGER_REQUESTS)
                    .addRow(PASSENGER_PROFILE)
                    .addRow(PASSENGER_TO_DRIVER)
                    .build();
        }
    };
    //endregion

    private static final String MARKDOWN_V2 = "MarkdownV2";
    private static final ReplyKeyboard REPLY_KEYBOARD_REMOVE = new ReplyKeyboardRemove(true);

    BotUserService botUserService;
    DriverTripService driverTripService;

    private void setServices(BotUserService botUserService, DriverTripService driverTripService) {
        this.botUserService = botUserService;
        this.driverTripService = driverTripService;
    }

    public List<PartialBotApiMethod<Message>> getResponse(Message message) {
        return singletonList(SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup(message))
                .text(getText(message))
                .build());
    }

    public SendMessage getResponseWithCustomText(Message message, String text) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup(message))
                .text(text)
                .build();
    }

    public boolean isUserInputPage() {
        return false;
    }

    public abstract String getText(Message message);

    public abstract ReplyKeyboard getReplyMarkup(Message message);

    @Component
    private static class KeyboardPageServicesComponent {
        @Autowired
        private BotUserService botUserService;
        @Autowired
        private DriverTripService driverTripService;

        @PostConstruct
        public void postConstruct() {
            for (KeyboardPage kp : KeyboardPage.values()) {
                kp.setServices(botUserService, driverTripService);
            }
        }
    }
}
