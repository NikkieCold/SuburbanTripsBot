package ua.nikkie.SuburbanTripsBot.navigation.inline_menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public enum InlineButton {
    CONTACT_LINK(null) {
        @Override
        public InlineKeyboardButton getInlineButton() {
            return InlineKeyboardButton.builder()
                    .text("@perceverancx")
                    .url("t.me/perceverancx")
                    .build();
        }
    },

    DRIVER_TRIP_DELETE(null) {
        @Override
        public InlineKeyboardButton getInlineButton() {
            return InlineKeyboardButton.builder()
                .text("\u1303 Видалити поїздку")
                .url("t.me/perceverancx")
                .build();
        }
    },

    DRIVER_TRIP_CHANGE_INFO(null) {
        @Override
        public InlineKeyboardButton getInlineButton() {
            return InlineKeyboardButton.builder()
                .text("\u1274 Змінити інформацію")
                .url("t.me/perceverancx")
                .build();
        }
    };

    private final String callbackData;

    InlineButton(String callbackData) {
        this.callbackData = callbackData;
    }

    public abstract InlineKeyboardButton getInlineButton();
}
