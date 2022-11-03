package ua.nikkie.SuburbanTripsBot.update_handlers;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineMessage.getInlineResponseWithButton;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_MY_TRIPS;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_CAR_MODEL_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_CAR_PHOTO_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_MENU;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_NAME_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_COMMENT_QUESTION;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_DATE_CHOOSING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_PRICE_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_SEATS_NUMBER_QUESTION;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_STOPS_THROUGH_QUESTION;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_STOP_FROM_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_STOP_TO_SPECIFYING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_TIME_CHOOSING;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.DRIVER_TRIP_TIME_RANGE_CHOOSING;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.enums.BotUserRegistrationStage;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;
import ua.nikkie.SuburbanTripsBot.entities.services.DriverTripService;
import ua.nikkie.SuburbanTripsBot.exceptions.NotParsableMessage;
import ua.nikkie.SuburbanTripsBot.exceptions.UnexpectedInput;
import ua.nikkie.SuburbanTripsBot.exceptions.UnexpectedSendMethod;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage.InputPage;
import ua.nikkie.SuburbanTripsBot.navigation.parsing.ParsedMessage;
import ua.nikkie.SuburbanTripsBot.util.SendMethodClass;

@Slf4j
@Component
public class MessageHandler {

    BotUserService botUserService;
    DriverTripService driverTripService;

    public MessageHandler(BotUserService botUserService, DriverTripService driverTripService) {
        this.botUserService = botUserService;
        this.driverTripService = driverTripService;
    }

    public void handle(DefaultAbsSender sender, Message message) throws TelegramApiException {
        try {
            if (isWaitingForUserInput(message)) {
                sendResponseList(sender, handleUserInputMessage(message));
                return;
            }
        } catch (UnexpectedInput e) {
            sendResponseList(sender, botUserService.getBotUser(message).getPage().getResponse(message));
            return;
        }

        try {
            sendResponseList(sender, getResponseByCalledButton(new ParsedMessage(message), message));
        } catch (NotParsableMessage e) {
            log.debug("Can't parse message with text: {}", message.getText(), e);
            sender.execute(buildNotCommandMessage(message));
        } catch (NullPointerException e) {
            sender.execute(buildPageNotCreatedMessage(message));
        }
    }

    private void sendResponseList(DefaultAbsSender sender, List<PartialBotApiMethod<Message>> responseList) {
        responseList.forEach(r -> {
            try {
                switch (SendMethodClass.get(r)) {
                    case SendMessage:
                        sender.execute((SendMessage) r);
                        break;
                    case SendPhoto:
                        sender.execute((SendPhoto) r);
                        break;
                    default:
                        throw new UnexpectedSendMethod(r);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private SendMessage buildNotCommandMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(String.format("%s, обери команду з меню!\nАбо натисни /start", message.getFrom().getFirstName()))
                .build();
    }

    private SendMessage buildPageNotCreatedMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Ця сторінка ще не була створена")
                .build();
    }

    private List<PartialBotApiMethod<Message>> getResponseByCalledButton(ParsedMessage parsedMessage, Message message) {
        KeyboardButton button = parsedMessage.getCalledButton();
        List<PartialBotApiMethod<Message>> response;

        switch (button) {
            case START_CONTACT:
                return getInlineResponseWithButton(message, button);
            case DRIVER_ACTIVE:
            case DRIVER_CREATE:
            case DRIVER_MY_TRIPS_BUTTON:
            case DRIVER_PROFILE:
                response = getDriverProfileAccessPage(message, parsedMessage);
                break;
            default:
                response = parsedMessage.getTargetPage().getResponse(message);
        }

        botUserService.setPage(message, parsedMessage.getTargetPage());
        return response;
    }

    private List<PartialBotApiMethod<Message>> getDriverProfileAccessPage(Message message, ParsedMessage parsedMessage) {
        BotUserRegistrationStage registrationStage = botUserService.getBotUser(message).getRegistrationStage();

        if (registrationStage == BotUserRegistrationStage.CAR_PHOTO) {
            return parsedMessage.getTargetPage().getResponse(message);
        }

        botUserService.setRegistrationCalledPage(message, parsedMessage.getTargetPage());

        switch (registrationStage) {
            case NOT_REGISTERED:
                parsedMessage.setTargetPage(DRIVER_PROFILE_NAME_SPECIFYING);
                break;
            case NAME:
                parsedMessage.setTargetPage(DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING);
                break;
            case PHONE_NUMBER:
                parsedMessage.setTargetPage(DRIVER_PROFILE_CAR_MODEL_SPECIFYING);
                break;
            case CAR_MODEL:
                parsedMessage.setTargetPage(DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING);
                break;
            case SEATS_NUMBER:
                parsedMessage.setTargetPage(DRIVER_PROFILE_CAR_PHOTO_SPECIFYING);
                break;
        }

        return parsedMessage.getTargetPage().getResponse(message);
    }

    private boolean isWaitingForUserInput(Message message) {
        BotUser user = botUserService.getBotUser(message);

        return nonNull(user.getPage()) && user.getPage().isUserInputPage() != InputPage.REGULAR;
    }

    private List<PartialBotApiMethod<Message>> handleUserInputMessage(Message message) throws UnexpectedInput {
        if (message.hasText() && message.getText().equals(KeyboardButton.START.getButtonText())) {
            KeyboardPage targetPage = KeyboardButton.START.getTargetPage();
            botUserService.setPage(message, targetPage);
            driverTripService.deleteUnfinishedTrips(message);
            return targetPage.getResponse(message);
        }
        if (message.hasText() && message.getText().equals(KeyboardButton.DRIVER_BACK_TO_MAIN_MENU.getButtonText())) {
            KeyboardPage targetPage = KeyboardButton.DRIVER_BACK_TO_MAIN_MENU.getTargetPage();
            botUserService.setPage(message, targetPage);
            driverTripService.deleteUnfinishedTrips(message);
            return targetPage.getResponse(message);
        }

        KeyboardPage page = botUserService.getBotUser(message).getPage();

        switch (page.isUserInputPage()) {
            case DRIVER_PROFILE_INPUT:
                return handleProfileSpecifying(message);
            case DRIVER_TRIP_CREATE_INPUT:
                return handleTripSpecifying(message);
            case REGULAR:
            default: throw new UnexpectedInput();
        }
    }

    private List<PartialBotApiMethod<Message>> handleProfileSpecifying(Message message) throws UnexpectedInput {
        BotUser botUser = botUserService.getBotUser(message);
        KeyboardPage nextPage = DRIVER_PROFILE_MENU;

        if (message.hasText()) {
            switch (botUser.getPage()) {
                case DRIVER_PROFILE_NAME_SPECIFYING:
                    if (message.getText().length() > 50) {
                        return singletonList(botUser.getPage().getResponseWithCustomText(
                            message, botUser.getPage().getText(message)
                            .concat("\nІм'я не має бути довшим за 50 символів!")));
                    }
                    botUserService.setName(message);
                    if (botUser.getRegistrationStage() != BotUserRegistrationStage.CAR_PHOTO) {
                        botUserService.setRegistrationStage(message, BotUserRegistrationStage.NAME);
                        nextPage = DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING;
                    }
                    break;
                case DRIVER_PROFILE_PHONE_NUMBER_SPECIFYING:
                    botUserService.setPhoneNumber(message);
                    if (botUser.getRegistrationStage() != BotUserRegistrationStage.CAR_PHOTO) {
                        botUserService.setRegistrationStage(message, BotUserRegistrationStage.PHONE_NUMBER);
                        nextPage = DRIVER_PROFILE_CAR_MODEL_SPECIFYING;
                    }
                    break;
                case DRIVER_PROFILE_CAR_MODEL_SPECIFYING:
                    botUserService.setCarModel(message);
                    if (botUser.getRegistrationStage() != BotUserRegistrationStage.CAR_PHOTO) {
                        botUserService.setRegistrationStage(message, BotUserRegistrationStage.CAR_MODEL);
                        nextPage = DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING;
                    }
                    break;
                case DRIVER_PROFILE_SEATS_NUMBER_SPECIFYING:
                    botUserService.setSeatsNumber(message);
                    if (botUser.getRegistrationStage() != BotUserRegistrationStage.CAR_PHOTO) {
                        botUserService.setRegistrationStage(message, BotUserRegistrationStage.SEATS_NUMBER);
                        nextPage = DRIVER_PROFILE_CAR_PHOTO_SPECIFYING;
                    }
                    break;
                default:
                    throw new UnexpectedInput();
            }
        } else if (message.hasPhoto()) {
            if (botUser.getPage() != DRIVER_PROFILE_CAR_PHOTO_SPECIFYING) {
                throw new UnexpectedInput();
            }
            botUserService.setCarPhoto(message);
            botUserService.setRegistrationStage(message, BotUserRegistrationStage.CAR_PHOTO);
            nextPage = botUserService.getBotUser(message).getRegistrationCalledPage();
        } else {
            throw new UnexpectedInput();
        }

        botUserService.setPage(message, nextPage);
        return nextPage.getResponse(message);
    }

    private List<PartialBotApiMethod<Message>> handleTripSpecifying(Message message) throws UnexpectedInput {
        BotUser botUser = botUserService.getBotUser(message);
        KeyboardPage page = botUser.getPage();
        KeyboardPage nextPage;

        if (!message.hasText()) {
            throw new UnexpectedInput();
        }

        switch (page) {
            case DRIVER_TRIP_STOPS_THROUGH_QUESTION:
            case DRIVER_TRIP_COMMENT_QUESTION:
            case DRIVER_TRIP_SEATS_NUMBER_QUESTION:
                try {
                    nextPage = new ParsedMessage(message).getTargetPage();
                    break;
                } catch (NotParsableMessage e) {
                    throw new UnexpectedInput();
                }
            case DRIVER_TRIP_DESTINATION_CHOOSING:
                driverTripService.setDestination(message);
                nextPage = DRIVER_TRIP_STOP_FROM_SPECIFYING;
                break;
            case DRIVER_TRIP_STOP_FROM_SPECIFYING:
                try {
                    nextPage = new ParsedMessage(message).getTargetPage();
                    driverTripService.deleteUnfinishedTrips(message);
                } catch (NotParsableMessage e) {
                    driverTripService.setStopFrom(message);
                    nextPage = DRIVER_TRIP_STOP_TO_SPECIFYING;
                }
                break;
            case DRIVER_TRIP_STOP_TO_SPECIFYING:
                driverTripService.setStopTo(message);
                nextPage = DRIVER_TRIP_STOPS_THROUGH_QUESTION;
                break;
            case DRIVER_TRIP_STOPS_THROUGH_SPECIFYING:
                driverTripService.setStopsThrough(message);
                nextPage = DRIVER_TRIP_COMMENT_QUESTION;
                break;
            case DRIVER_TRIP_COMMENT_SPECIFYING:
                driverTripService.setComment(message);
                nextPage = DRIVER_TRIP_SEATS_NUMBER_QUESTION;
                break;
            case DRIVER_TRIP_SEATS_NUMBER_SPECIFYING:
                driverTripService.setSeatsNumber(message);
                nextPage = DRIVER_TRIP_PRICE_SPECIFYING;
                break;
            case DRIVER_TRIP_PRICE_SPECIFYING:
                driverTripService.setPrice(message);
                nextPage = DRIVER_TRIP_DATE_CHOOSING;
                break;
            case DRIVER_TRIP_DATE_CHOOSING:
                driverTripService.setDate(message);
                nextPage = DRIVER_TRIP_TIME_RANGE_CHOOSING;
                break;
            case DRIVER_TRIP_TIME_RANGE_CHOOSING:
                driverTripService.setTimeRange(message);
                nextPage = DRIVER_TRIP_TIME_CHOOSING;
                break;
            case DRIVER_TRIP_TIME_CHOOSING:
                driverTripService.setTime(message);
                nextPage = DRIVER_MY_TRIPS;
                break;
            default:
                driverTripService.deleteUnfinishedTrips(message);
                throw new UnexpectedInput();
        }

        botUserService.setPage(message, nextPage);
        return nextPage.getResponse(message);
    }
}
