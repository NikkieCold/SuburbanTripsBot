package ua.nikkie.SuburbanTripsBot.navigation;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplyKeyboardBuilder {

    private final List<KeyboardRow> rowsList = new ArrayList<>();

    private ReplyKeyboardBuilder() {
    }

    public static ReplyKeyboardBuilder builder() {
        return new ReplyKeyboardBuilder();
    }

    public ReplyKeyboardMarkup build() {
        return new ReplyKeyboardMarkup(rowsList);
    }

    public ReplyKeyboardBuilder addRow(String... buttons) {
        rowsList.add(new KeyboardRow());
        rowsList.get(rowsList.size() - 1)
            .addAll(Arrays.asList(buttons));

        return this;
    }
}
