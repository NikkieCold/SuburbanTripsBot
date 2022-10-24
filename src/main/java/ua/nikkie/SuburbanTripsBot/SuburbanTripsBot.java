package ua.nikkie.SuburbanTripsBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.nikkie.SuburbanTripsBot.config.BotConfiguration;

@SpringBootApplication
public class SuburbanTripsBot extends TelegramWebhookBot {

    private final BotConfiguration botConfiguration;
    private final UpdateProcessor updateProcessor;

    public SuburbanTripsBot(BotConfiguration botConfiguration, UpdateProcessor updateProcessor)
        throws TelegramApiException {
        this.botConfiguration = botConfiguration;
        this.updateProcessor = updateProcessor;
        setWebhook(botConfiguration.getWebhook());
    }

    public static void main(String[] args) {
        SpringApplication.run(SuburbanTripsBot.class, args);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        updateProcessor.process(this, update);
        return null;
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getUsername();
    }

    @Override
    public String getBotPath() {
        return botConfiguration.getPath();
    }
}
