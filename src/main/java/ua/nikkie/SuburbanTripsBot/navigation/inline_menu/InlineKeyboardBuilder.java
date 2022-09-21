package ua.nikkie.SuburbanTripsBot.navigation.inline_menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InlineKeyboardBuilder {

    private final List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();

    private InlineKeyboardBuilder() {
    }

    public static InlineKeyboardBuilder builder() {
        return new InlineKeyboardBuilder();
    }

    public InlineKeyboardMarkup build() {
        return new InlineKeyboardMarkup(rowsList);
    }

    public InlineKeyboardBuilder addRow(InlineButton... buttons) {
        rowsList.add(new ArrayList<>());
        rowsList.get(rowsList.size() - 1)
                .addAll(Arrays.stream(buttons)
                        .map(InlineButton::getInlineButton)
                        .collect(Collectors.toList()));
        return this;
    }
}
