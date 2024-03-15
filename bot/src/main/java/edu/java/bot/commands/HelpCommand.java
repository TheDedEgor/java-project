package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements BotCommand {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        return new SendMessage(
            chatId,
            """
                Список доступных комманд:
                /start - регистрация пользователя
                /delete - удаление пользователя со всеми ссылками
                /track - отслеживание ссылки
                /untrack - прекращение отслеживания ссылки
                /list - список отслеживаемых ссылок
                """
        );
    }
}
