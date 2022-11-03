package ua.nikkie.SuburbanTripsBot.entities.enums;

import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_DESTINATION_TO_KYIV;
import static ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton.DRIVER_TRIP_CREATE_DESTINATION_TO_VASYLKIV;

import java.util.Arrays;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.exceptions.UnexpectedInput;

public enum DriverTripDestination {
    KYIV_TO_VASYLKIV(DRIVER_TRIP_CREATE_DESTINATION_TO_VASYLKIV.getButtonText(), "Києві", "Василькові"),
    VASYLKIV_TO_KYIV(DRIVER_TRIP_CREATE_DESTINATION_TO_KYIV.getButtonText(), "Василькові", "Києві");

    private final String displayDestination;
    private final String fromCity;

    private final String toCity;

    DriverTripDestination(String displayDestination, String fromCity, String toCity) {
        this.displayDestination = displayDestination;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public static DriverTripDestination parseMessage(Message message) throws UnexpectedInput {
        return Arrays.stream(DriverTripDestination.values())
            .filter(destination -> message.getText().equals(destination.displayDestination))
            .findAny().orElseThrow(UnexpectedInput::new);
    }
}
