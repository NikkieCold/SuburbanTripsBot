package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReplyKeyboardBuilder {

    private final List<KeyboardRow> rowsList = new ArrayList<>();

    private ReplyKeyboardBuilder() {
    }

    public static ReplyKeyboardBuilder builder() {
        return new ReplyKeyboardBuilder();
    }

    public ReplyKeyboardMarkup build() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rowsList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardBuilder addRow(KeyboardButton... buttons) {
        rowsList.add(new KeyboardRow());
        rowsList.get(rowsList.size() - 1)
            .addAll(Arrays.stream(buttons)
                    .map(KeyboardButton::getButtonText)
                    .collect(Collectors.toList()));

        return this;
    }
}
