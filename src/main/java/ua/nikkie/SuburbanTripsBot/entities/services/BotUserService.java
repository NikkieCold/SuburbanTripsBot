package ua.nikkie.SuburbanTripsBot.entities.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.repositories.BotUserRepository;

public class BotUserService {

    BotUserRepository botUserRepository;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    private void addNewUser(Message message) {
        botUserRepository.save(new BotUser(message.getChatId()));
    }

    public BotUser getUser(Message message) {
        return botUserRepository.findBotUserByChatId(message.getChatId()).orElseGet(() -> {
            addNewUser(message);
            return getUser(message);
        });
    }
}
