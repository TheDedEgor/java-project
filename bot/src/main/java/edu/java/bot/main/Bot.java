package edu.java.bot.main;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    @Autowired
    private ApplicationConfig  applicationConfig;

    @EventListener(ApplicationReadyEvent.class)
    public void runBot() {
        var bot = new TelegramBot(applicationConfig.telegramToken());
        bot.setUpdatesListener(new BotUpdateListener(bot), new BotExceptionHandler());
    }
}
