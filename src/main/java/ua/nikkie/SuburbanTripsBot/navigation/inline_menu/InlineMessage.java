package ua.nikkie.SuburbanTripsBot.navigation.inline_menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;

import java.util.Arrays;

import static java.util.Objects.nonNull;
import static ua.nikkie.SuburbanTripsBot.navigation.inline_menu.InlineButton.CONTACT_LINK;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.START_CONTACT;

public enum InlineMessage {
    CONTACT(START_CONTACT) {
        @Override
        String getText() {
            return "Щоб написати розробнику бота натисни на кнопку внизу:";
        }

        @Override
        InlineKeyboardMarkup getReplyMarkup() {
            return InlineKeyboardBuilder.builder()
                    .addRow(CONTACT_LINK)
                    .build();
        }
    };

    private final KeyboardButton callButton;

    InlineMessage() {
        callButton = null;
    }

    InlineMessage(KeyboardButton callButton) {
        this.callButton = callButton;
    }

    public static SendMessage getInitInlineResponse(Message message, KeyboardButton calledButton) {
        return Arrays.stream(values())
                .filter(inlineMessage -> nonNull(inlineMessage.callButton))
                .filter(inlineMessage -> inlineMessage.callButton == calledButton)
                .map(inlineMessage -> inlineMessage.getResponse(message))
                .findAny().orElseThrow(RuntimeException::new);

    }

    public SendMessage getResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getText())
                .replyMarkup(getReplyMarkup())
                .build();
    }

    abstract String getText();

    abstract InlineKeyboardMarkup getReplyMarkup();
}
