package ua.nikkie.SuburbanTripsBot.navigation.parsing;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.exceptions.NotParsableMessage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;

import java.util.Arrays;

import static java.util.Objects.nonNull;

public class ParsedMessage {

    private KeyboardPage targetPage;
    private final KeyboardButton calledButton;

    public ParsedMessage(Message message) throws NotParsableMessage {
        targetPage = parseTargetPage(message);
        calledButton = parseCalledButton(message);
    }

    public void setTargetPage(KeyboardPage targetPage) {
        this.targetPage = targetPage;
    }

    public KeyboardPage getTargetPage() {
        return targetPage;
    }

    public KeyboardButton getCalledButton() {
        return calledButton;
    }

    private KeyboardPage parseTargetPage(Message message) throws NotParsableMessage {
        return Arrays.stream(KeyboardButton.values())
                .filter(button -> nonNull(button.getButtonText()))
                .filter(button -> button.getButtonText().equals(message.getText()))
                .map(KeyboardButton::getTargetPage)
                .findAny()
                .orElseThrow(NotParsableMessage::new);
    }

    private KeyboardButton parseCalledButton(Message message) throws NotParsableMessage {
        return Arrays.stream(KeyboardButton.values())
                .filter(button -> nonNull(button.getButtonText()))
                .filter(button -> button.getButtonText().equals(message.getText()))
                .findAny()
                .orElseThrow(NotParsableMessage::new);
    }
}
