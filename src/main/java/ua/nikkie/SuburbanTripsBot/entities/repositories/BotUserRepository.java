package ua.nikkie.SuburbanTripsBot.entities.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findBotUserByChatId(Long chatId);
}