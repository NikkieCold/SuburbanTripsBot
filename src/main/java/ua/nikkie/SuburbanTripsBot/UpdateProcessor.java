package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateProcessor {

    @SneakyThrows
    public void process(DefaultAbsSender sender, Update update) {

    }
}
