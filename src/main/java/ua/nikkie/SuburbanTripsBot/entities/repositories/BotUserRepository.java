package ua.nikkie.SuburbanTripsBot.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findBotUserByChatId(Long chatId);
}