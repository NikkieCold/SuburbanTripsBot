package ua.nikkie.SuburbanTripsBot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.nikkie.SuburbanTripsBot.SuburbanTripsBot;

@RestController
public class WebhookController {

    private final SuburbanTripsBot bot;

    public WebhookController(SuburbanTripsBot bot) {
        this.bot = bot;
    }

    @PostMapping(value = "/callback")
    public void onUpdateReceived(@RequestBody Update update) {
        bot.onWebhookUpdateReceived(update);
    }
}
