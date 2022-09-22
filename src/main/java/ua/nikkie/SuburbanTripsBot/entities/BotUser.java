package ua.nikkie.SuburbanTripsBot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import ua.nikkie.SuburbanTripsBot.entities.enums.BotUserRegistrationStage;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;

import javax.persistence.*;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

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
    String role;
    @Enumerated(EnumType.STRING)
    BotUserRegistrationStage registrationStage = BotUserRegistrationStage.NOT_REGISTERED;
    @Enumerated(EnumType.STRING)
    KeyboardPage registrationCalledPage;
    String name;
    String phoneNumber;
    String carModel;
    String seatsNumber;
    String carPhoto;

    public BotUser(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "BotUser{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", page=" + page +
                ", role='" + role + '\'' +
                ", registrationStage=" + registrationStage +
                ", registrationCalledPage=" + registrationCalledPage +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carModel='" + carModel + '\'' +
                ", seatsNumber='" + seatsNumber + '\'' +
                ", carPhoto='" + carPhoto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BotUser botUser = (BotUser) o;
        return userId != null && Objects.equals(userId, botUser.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
