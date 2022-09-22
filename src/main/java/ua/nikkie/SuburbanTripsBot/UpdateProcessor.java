package ua.nikkie.SuburbanTripsBot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.nikkie.SuburbanTripsBot.update_handlers.CallbackHandler;
import ua.nikkie.SuburbanTripsBot.update_handlers.MessageHandler;

@Slf4j
@Service
public class UpdateProcessor {

    MessageHandler messageHandler;
    CallbackHandler callbackHandler;

    public UpdateProcessor(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public void process(DefaultAbsSender sender, Update update) {
        try {
            if (update.hasMessage()) {
                messageHandler.handle(sender, update.getMessage());
            } else if (update.hasCallbackQuery()) {
                callbackHandler.handle(sender, update.getCallbackQuery());
            }
        } catch (TelegramApiException e) {
            log.error("Error while executing response", e);
        }
    }
}
