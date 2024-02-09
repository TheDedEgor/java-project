package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class DefaultCommand implements BotCommand {
    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        return new SendMessage(chatId, "Неизвестная команда, введите /help для списка доступных команд");
    }
}
