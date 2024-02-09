package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpCommand implements BotCommand {
    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        return new SendMessage(
            chatId,
            "Список доступных комманд:\n"
                +
                "/start - регистрация пользователя\n"
                +
                "/track - отслеживание ссылки\n"
                +
                "/untrack - прекращение отслеживания ссылки\n"
                +
                "/list - список отслеживаемых ссылок"
        );
    }
}
