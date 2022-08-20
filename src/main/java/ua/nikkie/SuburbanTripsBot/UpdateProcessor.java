package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineMessage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;
import ua.nikkie.SuburbanTripsBot.navigation.parsing.ParsedMessage;

@Service
public class UpdateProcessor {

    @SneakyThrows
    public void process(DefaultAbsSender sender, Update update) {
        if (update.hasMessage()) {
            handleMessage(sender, update);
        } else if (update.hasCallbackQuery()) {
            handleCallback(sender, update);
        }
    }

    @SneakyThrows
    private void handleMessage(DefaultAbsSender sender, Update update) {
        ParsedMessage parsedMessage = new ParsedMessage(update.getMessage());
        Message message = update.getMessage();
        SendMessage sendMessage;

        if (parsedMessage.getTargetPage() == KeyboardPage.INLINE_KEYBOARD_MESSAGE) {
            sendMessage = InlineMessage.getInitInlineResponse(message, parsedMessage.getCalledButton());
        } else {
            sendMessage = parsedMessage.getTargetPage().getResponse(message);
        }

        sender.execute(sendMessage);
    }

    @SneakyThrows
    private void handleCallback(DefaultAbsSender sender, Update update) {

    }
}
