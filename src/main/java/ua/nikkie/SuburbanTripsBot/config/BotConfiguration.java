package ua.nikkie.SuburbanTripsBot.config;

import static ua.nikkie.SuburbanTripsBot.util.BotUtil.RESOURCES_PATH;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
public class BotConfiguration {

    private final String username;
    private final String token;
    private final String path;
    private final SetWebhook webhook;

    public BotConfiguration(@Value("${BOT_USERNAME}") String username,
                            @Value("${BOT_TOKEN}") String token,
                            @Value("${BOT_SERVER}:${SERVER_PORT}") String botUrl,
                            @Value("${CERTIFICATE_PATH}") String publicKey) {
        this.username = username;
        this.token = token;
        this.path = null;
        this.webhook = SetWebhook.builder()
            .url(botUrl)
            .certificate(new InputFile(new File(RESOURCES_PATH + publicKey)))
            .build();
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getPath() {
        return path;
    }

    public SetWebhook getWebhook() {
        return webhook;
    }
}
