package ua.nikkie.SuburbanTripsBot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SuburbanTripsBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final UpdateProcessor updateProcessor;

    public SuburbanTripsBot(
            @Value("${telegram.api.botUsername}") String botUsername,
            @Value("${telegram.api.botToken}") String botToken,
            UpdateProcessor updateProcessor) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.updateProcessor = updateProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(SuburbanTripsBot.class, args);
    }

    @PostConstruct @SneakyThrows
    public void sendStartUpMessage() {
        execute(SendMessage.builder()
                .chatId("339893955")
                .text("Started").build());
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.process(this, update);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
