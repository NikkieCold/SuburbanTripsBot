package ua.nikkie.SuburbanTripsBot.entities.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findBotUserByChatId(Long chatId);
}