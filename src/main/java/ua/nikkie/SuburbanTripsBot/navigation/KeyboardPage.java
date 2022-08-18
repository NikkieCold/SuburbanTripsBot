package ua.nikkie.SuburbanTripsBot.navigation;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyboardPage {

    default SendMessage getResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .replyMarkup(getReplyMarkup())
                .text(getText())
                .build();
    }

    default ReplyKeyboard getReplyMarkup() {
        return null;
    }

    String getText();
}
