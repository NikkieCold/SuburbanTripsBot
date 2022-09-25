package ua.nikkie.SuburbanTripsBot.entities.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.enums.BotUserRegistrationStage;
import ua.nikkie.SuburbanTripsBot.entities.repositories.BotUserRepository;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardPage;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BotUserService {

    BotUserRepository botUserRepository;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    private void addNewUser(Message message) {
        botUserRepository.save(new BotUser(message.getChatId()));
    }

    public BotUser getBotUser(Message message) {
        return botUserRepository.findBotUserByChatId(message.getChatId()).orElseGet(() -> {
            addNewUser(message);
            return getBotUser(message);
        });
    }

    @Transactional
    public void setPage(Message message, KeyboardPage keyboardPage) {
        if (keyboardPage != KeyboardPage.INLINE_KEYBOARD_MESSAGE) {
            getBotUser(message).setPage(keyboardPage);
        }
    }

    @Transactional
    public void setRegistrationCalledPage(Message message, KeyboardPage keyboardPage) {
        getBotUser(message).setRegistrationCalledPage(keyboardPage);
    }

    @Transactional
    public void setRegistrationStage(Message message, BotUserRegistrationStage registrationStage) {
        getBotUser(message).setRegistrationStage(registrationStage);
    }

    @Transactional
    public void setName(Message message) {
        getBotUser(message).setName(message.getText());
    }

    @Transactional
    public void setPhoneNumber(Message message) {
        getBotUser(message).setPhoneNumber(message.getText());
    }

    @Transactional
    public void setCarModel(Message message) {
        getBotUser(message).setCarModel(message.getText());
    }

    @Transactional
    public void setSeatsNumber(Message message) {
        getBotUser(message).setSeatsNumber(message.getText());
    }

    @Transactional
    public void setCarPhoto(Message message) {
        List<PhotoSize> photoSizes = message.getPhoto();
        getBotUser(message).setCarPhoto(photoSizes.get(photoSizes.size()-1).getFileId());
    }
}
