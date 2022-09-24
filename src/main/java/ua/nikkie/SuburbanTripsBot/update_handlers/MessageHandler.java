package ua.nikkie.SuburbanTripsBot.update_handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.enums.BotUserRegistrationStage;
import ua.nikkie.SuburbanTripsBot.entities.services.BotUserService;
import ua.nikkie.SuburbanTripsBot.exceptions.NotParsableMessage;
import ua.nikkie.SuburbanTripsBot.exceptions.UnexpectedPage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;
import ua.nikkie.SuburbanTripsBot.navigation.parsing.ParsedMessage;

import static java.util.Objects.nonNull;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineMessage.getInlineResponseWithButton;

@Slf4j
@Component
public class MessageHandler {

    BotUserService botUserService;

    public MessageHandler(BotUserService botUserService) {
        this.botUserService = botUserService;
    }

    public void handle(DefaultAbsSender sender, Message message) throws TelegramApiException {
        if (isWaitingForTextPage(message)) {
            try {
                sender.execute(handleUserTextMessage(message));
            } catch (UnexpectedPage e) {
                throw new RuntimeException();
            }
            return;
        }

        try {
            sender.execute(getResponseByCalledButton(new ParsedMessage(message), message));
        } catch (NotParsableMessage e) {
            log.debug("Can't parse message with text: {}", message.getText(), e);
            sender.execute(buildNotCommandMessage(message));
        } catch (NullPointerException e) {
            sender.execute(buildPageNotCreatedMessage(message));
        }
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

    private SendMessage getResponseByCalledButton(ParsedMessage parsedMessage, Message message) {
        KeyboardButton button = parsedMessage.getCalledButton();
        SendMessage response;

        switch (button) {
            case START_CONTACT:
                return getInlineResponseWithButton(message, button);
            case DRIVER_ACTIVE:
            case DRIVER_CREATE:
            case DRIVER_TRIPS:
            case DRIVER_PROFILE:
                response = getDriverProfileAccessPage(message, parsedMessage);
                break;
            default:
                response = parsedMessage.getTargetPage().getResponse(message);
        }

        botUserService.setPage(message, parsedMessage.getTargetPage());
        return response;
    }

    private SendMessage getDriverProfileAccessPage(Message message, ParsedMessage parsedMessage) {
        BotUserRegistrationStage registrationStage = botUserService.getBotUser(message).getRegistrationStage();

        if (registrationStage == BotUserRegistrationStage.CAR_PHOTO) {
            return parsedMessage.getTargetPage().getResponse(message);
        }

        botUserService.setRegistrationCalledPage(message, parsedMessage.getTargetPage());

        switch (registrationStage) {
            case NOT_REGISTERED:
                parsedMessage.setTargetPage(KeyboardPage.DRIVER_NAME_SPECIFYING);
                break;
            case NAME:
                parsedMessage.setTargetPage(KeyboardPage.DRIVER_PHONE_NUMBER_SPECIFYING);
                break;
            case PHONE_NUMBER:
                parsedMessage.setTargetPage(KeyboardPage.DRIVER_CAR_MODEL_SPECIFYING);
                break;
            case CAR_MODEL:
                parsedMessage.setTargetPage(KeyboardPage.DRIVER_SEATS_NUMBER_SPECIFYING);
                break;
            case SEATS_NUMBER:
                parsedMessage.setTargetPage(KeyboardPage.DRIVER_CAR_PHOTO_SPECIFYING);
                break;
        }

        return parsedMessage.getTargetPage().getResponse(message);
    }

    private boolean isWaitingForTextPage(Message message) {
        BotUser user = botUserService.getBotUser(message);
        return nonNull(user.getPage()) && user.getPage().getResponse(message).getReplyMarkup()
                .getClass() == ReplyKeyboardRemove.class;
    }

    private SendMessage handleUserTextMessage(Message message) throws UnexpectedPage {
        if (message.getText().equals(KeyboardButton.START.getButtonText())) {
            botUserService.setPage(message, KeyboardButton.START.getTargetPage());
            return KeyboardButton.START.getTargetPage().getResponse(message);
        }

        KeyboardPage page = botUserService.getBotUser(message).getPage();
        KeyboardPage nextPage;

        switch (page) {
            case DRIVER_NAME_SPECIFYING:
                if (message.getText().length() > 50) {
                    return page.getResponseWithCustomText(message, page.getText(message)
                            .concat("\nІм'я не має бути довшим за 50 символів!"));
                }
                botUserService.setName(message);
                botUserService.setRegistrationStage(message, BotUserRegistrationStage.NAME);
                nextPage = KeyboardPage.DRIVER_PHONE_NUMBER_SPECIFYING;
                break;
            case DRIVER_PHONE_NUMBER_SPECIFYING:
                botUserService.setPhoneNumber(message);
                botUserService.setRegistrationStage(message, BotUserRegistrationStage.PHONE_NUMBER);
                nextPage = KeyboardPage.DRIVER_CAR_MODEL_SPECIFYING;
                break;
            case DRIVER_CAR_MODEL_SPECIFYING:
                botUserService.setCarModel(message);
                botUserService.setRegistrationStage(message, BotUserRegistrationStage.CAR_MODEL);
                nextPage = KeyboardPage.DRIVER_SEATS_NUMBER_SPECIFYING;
                break;
            case DRIVER_SEATS_NUMBER_SPECIFYING:
                botUserService.setSeatsNumber(message);
                botUserService.setRegistrationStage(message, BotUserRegistrationStage.SEATS_NUMBER);
                nextPage = KeyboardPage.DRIVER_CAR_PHOTO_SPECIFYING;
                break;
            case DRIVER_CAR_PHOTO_SPECIFYING:
                botUserService.setCarPhoto(message);
                botUserService.setRegistrationStage(message, BotUserRegistrationStage.CAR_PHOTO);
                nextPage = botUserService.getBotUser(message).getRegistrationCalledPage();
                break;
            default:
                throw new UnexpectedPage();
        }

        botUserService.setPage(message, nextPage);
        return nextPage.getResponse(message);
    }
}
