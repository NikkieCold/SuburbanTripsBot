package ua.nikkie.SuburbanTripsBot.update_handlers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.exceptions.NotParsableMessage;
import ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineMessage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;
import ua.nikkie.SuburbanTripsBot.navigation.parsing.ParsedMessage;

@Slf4j
@Component
public class MessageHandler {

    private static final String NOT_COMMAND_MESSAGE_FORMAT = "%s, обери команду з меню!\nАбо натисни /start";

    @SneakyThrows
    public void handle(DefaultAbsSender sender, Message message) {
        ParsedMessage parsedMessage;
        try {
            parsedMessage = new ParsedMessage(message);
        } catch (NotParsableMessage e) {
            log.debug("Can't parse message with text: {}", message.getText(), e);
            sender.execute(buildNotCommandMessage(message));
            return;
        }

        SendMessage sendMessage;
        if (parsedMessage.getTargetPage() == KeyboardPage.INLINE_KEYBOARD_MESSAGE) {
            sendMessage = InlineMessage.getInlineResponseWithButton(message, parsedMessage.getCalledButton());
        } else {
            sendMessage = parsedMessage.getTargetPage().getResponse(message);
        }

        sender.execute(sendMessage);
    }

    private SendMessage buildNotCommandMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(String.format(NOT_COMMAND_MESSAGE_FORMAT, message.getFrom().getFirstName()))
                .build();
    }
}
