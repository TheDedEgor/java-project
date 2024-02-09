package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.List;

public class StartCommand implements BotCommand {

    // TODO имитация данных в бд
    private static List<Long> listIds = new ArrayList<>();

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        if (listIds.contains(chatId)) {
            return new SendMessage(chatId, "Пользователь уже зарегестрирован");
        }
        listIds.add(chatId);
        return new SendMessage(chatId, "Пользователь успешно зарегистрирован");
    }
}
