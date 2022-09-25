package ua.nikkie.SuburbanTripsBot.exceptions;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnexpectedSendMethod extends RuntimeException {

    public UnexpectedSendMethod(PartialBotApiMethod<Message> response) {
        super("Unexpected response: " + response);
    }
}
