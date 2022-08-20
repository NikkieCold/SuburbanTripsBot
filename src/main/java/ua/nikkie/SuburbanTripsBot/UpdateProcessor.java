package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.KeyboardButton;

@Service
public class UpdateProcessor {

    @SneakyThrows
    public void process(DefaultAbsSender sender, Update update) {
        Message message = update.getMessage();

        SendMessage sendMessage = KeyboardButton.parseMessage(message).getResponse(message);
        sender.execute(sendMessage);
    }
}
