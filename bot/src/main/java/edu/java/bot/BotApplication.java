package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.main.BotExceptionHandler;
import edu.java.bot.main.BotUpdateListener;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    @Autowired
    private TelegramBot bot;

    @Autowired
    private BotUpdateListener botUpdateListener;

    @Autowired
    private BotExceptionHandler botExceptionHandler;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runBot() {
        bot.setUpdatesListener(botUpdateListener, botExceptionHandler);
        Metrics.addRegistry(new SimpleMeterRegistry());
    }
}
