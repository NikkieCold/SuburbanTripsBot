package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.nikkie.SuburbanTripsBot.exceptions.NotParsableMessage;
import ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineMessage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;
import ua.nikkie.SuburbanTripsBot.navigation.parsing.ParsedMessage;

@Service
public class UpdateProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProcessor.class);

    private static final String NOT_COMMAND_MESSAGE_FORMAT = "%s, обери команду з меню!\nАбо натисни /start";

    @SneakyThrows
    public void process(DefaultAbsSender sender, Update update) {
        if (update.hasMessage()) {
            handleMessage(sender, update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallback(sender, update.getCallbackQuery());
        }
    }

    @SneakyThrows
    private void handleMessage(DefaultAbsSender sender, Message message) {
        ParsedMessage parsedMessage;
        try {
            parsedMessage = new ParsedMessage(message);
        } catch (NotParsableMessage e) {
            LOGGER.debug("Can't parse message with text: {}", message.getText(), e);
            sender.execute(buildNotCommandMessage(message));
            return;
        }

        SendMessage sendMessage;
        if (parsedMessage.getTargetPage() == KeyboardPage.INLINE_KEYBOARD_MESSAGE) {
            sendMessage = InlineMessage.getInitInlineResponse(message, parsedMessage.getCalledButton());
        } else {
            sendMessage = parsedMessage.getTargetPage().getResponse(message);
        }

        sender.execute(sendMessage);
    }

    @SneakyThrows
    private void handleCallback(DefaultAbsSender sender, CallbackQuery callbackQuery) {

    }

    private SendMessage buildNotCommandMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(String.format(NOT_COMMAND_MESSAGE_FORMAT, message.getFrom().getFirstName()))
                .build();
    }
}
