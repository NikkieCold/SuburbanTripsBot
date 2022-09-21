package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.nikkie.SuburbanTripsBot.update_handlers.CallbackHandler;
import ua.nikkie.SuburbanTripsBot.update_handlers.MessageHandler;

@Service
public class UpdateProcessor {

    MessageHandler messageHandler;
    CallbackHandler callbackHandler;

    public UpdateProcessor(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @SneakyThrows
    public void process(DefaultAbsSender sender, Update update) {
        if (update.hasMessage()) {
            messageHandler.handle(sender, update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handle(sender, update.getCallbackQuery());
        }
    }
}
