package ua.nikkie.SuburbanTripsBot.entities;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ua.nikkie.SuburbanTripsBot.entities.enums.BotUserRegistrationStage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Entity
public class BotUser {

    @Id
    @SequenceGenerator(
            name = "bot_user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bot_user_sequence")
    Long userId;

    Long chatId;

    @Enumerated(EnumType.STRING)
    KeyboardPage page;

    @Enumerated(EnumType.STRING)
    BotUserRegistrationStage registrationStage = BotUserRegistrationStage.NOT_REGISTERED;

    @Enumerated(EnumType.STRING)
    KeyboardPage registrationCalledPage;

    String name;

    String phoneNumber;

    String carModel;

    String seatsNumber;

    String carPhoto;

    @OneToMany(mappedBy = "driver", orphanRemoval = true)
    List<DriverTrip> driverTrips = new ArrayList<>();

    public BotUser(Long chatId) {
        this.chatId = chatId;
    }
}
