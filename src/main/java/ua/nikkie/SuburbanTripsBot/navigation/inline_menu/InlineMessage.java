package ua.nikkie.SuburbanTripsBot.navigation.inline_menu;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineButton.CONTACT_LINK;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineButton.DRIVER_TRIP_CHANGE_INFO;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineButton.DRIVER_TRIP_DELETE;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_CONTACT;
import static ua.nikkie.SuburbanTripsBot.util.BotUtil.DateTime.getUkrainianDayOfWeek;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;
import ua.nikkie.SuburbanTripsBot.entities.services.DriverTripService;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;

public enum InlineMessage {

    CONTACT(START_CONTACT) {
        @Override
        String getText() {
            return "Щоб написати розробнику бота натисни на кнопку внизу:";
        }

        @Override
        InlineKeyboardMarkup getReplyMarkup() {
            return InlineKeyboardBuilder.builder()
                    .addRow(CONTACT_LINK)
                    .build();
        }
    },

    MY_TRIPS {
        @Override
        String getText() {
            return "%s, %s\n\n"
                + "%s, %s.%s, %s:%s\n"
                + "%s:\n%s\n"
                + "Їду через:\n%s"
                + "%s:\n%s\n"
                + "%s\n"
                + "Вільних місць: %s\n"
                + "Вартість: %s\n\n"
                + "Коментар водія: \n%s";
            //TODO вартість, фото
        }

        @Override
        InlineKeyboardMarkup getReplyMarkup() {
            return InlineKeyboardBuilder.builder()
                .addRow(DRIVER_TRIP_CHANGE_INFO, DRIVER_TRIP_DELETE)
                .build();
        }

        @Override
        public List<PartialBotApiMethod<Message>> getResponse(Message message) {
            return driverTripService.getDriverTrips(message).stream()
                .map(t -> SendPhoto.builder()
                    .chatId(message.getChatId().toString())
                    .caption(String.format(getText(), t.getDriver().getName(), t.getDriver().getPhoneNumber(),
                        getUkrainianDayOfWeek(t), t.getDate().getDayOfMonth(),
                        t.getDate().getMonthValue(), t.getTime().getHour(), t.getTime().getMinute(),
                        t.getDestination().getDisplayDestination().split("->")[0].trim(), t.getStopFrom(),
                        t.getStopsThrough(),
                        t.getDestination().getDisplayDestination().split("->")[1].trim(), t.getStopTo(),
                        t.getDriver().getCarModel(),
                        t.getSeatsNumber(),
                        t.getPrice(),
                        t.getComment()))
                    .photo(new InputFile(t.getDriver().getCarPhoto()))
                    .replyMarkup(getReplyMarkup())
                    .build())
                .collect(Collectors.toList());
        }
    };

    private final KeyboardButton callButton;

    public BotUserService botUserService;
    public DriverTripService driverTripService;

    InlineMessage() {
        callButton = null;
    }

    InlineMessage(KeyboardButton callButton) {
        this.callButton = callButton;
    }

    private void setServices(BotUserService botUserService, DriverTripService driverTripService) {
        this.botUserService = botUserService;
        this.driverTripService = driverTripService;
    }

    public static List<PartialBotApiMethod<Message>> getInlineResponseWithButton(Message message, KeyboardButton calledButton) {
        return Arrays.stream(values())
                .filter(inlineMessage -> nonNull(inlineMessage.callButton))
                .filter(inlineMessage -> inlineMessage.callButton == calledButton)
                .map(inlineMessage -> inlineMessage.getResponse(message))
                .findAny().orElseThrow(IllegalArgumentException::new);

    }

    public List<PartialBotApiMethod<Message>> getResponse(Message message) {
        return singletonList(SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getText())
                .replyMarkup(getReplyMarkup())
                .build());
    }

    abstract String getText();

    abstract InlineKeyboardMarkup getReplyMarkup();

    @Component
    private static class BotUserServiceComponent {
        @Autowired
        private BotUserService botUserService;
        @Autowired
        private DriverTripService driverTripService;

        @PostConstruct
        public void postConstruct() {
            for (InlineMessage im : InlineMessage.values()) {
                im.setServices(botUserService, driverTripService);
            }
        }
    }
}
