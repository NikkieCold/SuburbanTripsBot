package ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class ReplyKeyboardStringBuilder {

    private final List<KeyboardRow> rowsList = new ArrayList<>();

    private ReplyKeyboardStringBuilder() {
    }

    public static ReplyKeyboardStringBuilder builder() {
        return new ReplyKeyboardStringBuilder();
    }

    public ReplyKeyboardMarkup build() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rowsList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardStringBuilder addRow(String... buttons) {
        rowsList.add(new KeyboardRow());
        rowsList.get(rowsList.size() - 1)
            .addAll(asList(buttons));

        return this;
    }
}
