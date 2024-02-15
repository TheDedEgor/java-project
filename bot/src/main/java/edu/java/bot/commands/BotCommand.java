package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BotCommand {
    String command();

    SendMessage handle(Update update);
}
