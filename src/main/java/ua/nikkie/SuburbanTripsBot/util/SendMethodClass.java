package ua.nikkie.SuburbanTripsBot.util;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public enum SendMethodClass {
    SendMessage, SendPhoto;

    public static SendMethodClass get(PartialBotApiMethod<Message> response) {
        return valueOf(response.getClass().getSimpleName());
    }
}
