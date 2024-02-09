package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;

public class UntrackCommand implements BotCommand {
    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var msg = update.message().text();
        var url = msg.split("\\s+")[1];
        var urls = TrackCommand.mapUrl.getOrDefault(chatId, new ArrayList<>());
        if (!urls.contains(url)) {
            return new SendMessage(chatId, "Ссылки нет в отслеживаемых");
        }
        urls.remove(url);
        return new SendMessage(chatId, "Ссылка успешно удалена");
    }
}
