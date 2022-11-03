package ua.nikkie.SuburbanTripsBot.entities;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ua.nikkie.SuburbanTripsBot.entities.enums.DriverTripDestination;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
public class DriverTrip {

    @Id
    @SequenceGenerator(
        name = "driver_trip_sequence",
        allocationSize = 1)
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "driver_trip_sequence")
    Long tripId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_bot_user_id", nullable = false)
    BotUser driver;

    Boolean isFinished = false;

    @Enumerated(EnumType.STRING)
    DriverTripDestination destination;

    String stopFrom;

    String stopTo;

    String stopsThrough;

    String price;

    String comment;

    LocalDate date;

    String timeRange;

    LocalTime time;

    String seatsNumber;

    public DriverTrip(BotUser driver) {
        this.driver = driver;
    }

    public String getPrice() {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(price);
        if (!matcher.find()) {
            return price.concat(" грн.");
        }
        return price;
    }

    public String getSeatsNumber() {
        if (nonNull(seatsNumber)) {
            return seatsNumber;
        }
        return driver.getSeatsNumber();
    }
}
