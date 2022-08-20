package ua.nikkie.SuburbanTripsBot.navigation.parsing;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class ParsedMessage {
    private final KeyboardPage targetPage;
    private final KeyboardButton calledButton;

    public ParsedMessage(Message message) {
        targetPage = parseTargetPage(message);
        calledButton = parseCalledButton(message);
    }

    public KeyboardPage getTargetPage() {
        return targetPage;
    }

    public KeyboardButton getCalledButton() {
        return calledButton;
    }

    private KeyboardPage parseTargetPage(Message message) {
        return Arrays.stream(KeyboardButton.values())
                .filter(button -> nonNull(button.getButtonText()))
                .filter(button -> button.getButtonText().equals(message.getText()))
                .map(KeyboardButton::getTargetPage)
                .findAny()
                .orElse(KeyboardPage.NOT_COMMAND);
    }

    private KeyboardButton parseCalledButton(Message message) {
        return Arrays.stream(KeyboardButton.values())
                .filter(button -> nonNull(button.getButtonText()))
                .filter(button -> button.getButtonText().equals(message.getText()))
                .findAny().orElse(KeyboardButton.NOT_COMMAND);
    }
}
