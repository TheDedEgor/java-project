package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;

public class ListCommand implements BotCommand {
    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var urls = TrackCommand.mapUrl.getOrDefault(chatId, new ArrayList<>());
        if (urls.isEmpty()) {
            return new SendMessage(chatId, "Список ссылок пуст");
        }
        return new SendMessage(chatId, "Список отслеживаемых ссылок:\n" + String.join("\n", urls));
    }
}
