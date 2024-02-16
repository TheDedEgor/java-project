package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.links.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements BotCommand {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var msg = update.message().text().split("\\s+");
        if (msg.length < 2) {
            return new SendMessage(chatId, "Введите ссылку после команды");
        }
        var url = msg[1];
        if (!linkRepository.existLink(chatId, url)) {
            return new SendMessage(chatId, "Ссылки нет в отслеживаемых");
        }
        var removed = linkRepository.untrackLink(chatId, url);
        if (removed) {
            return new SendMessage(chatId, "Ссылка успешно удалена");
        }
        return new SendMessage(chatId, "Произошла ошибка при удалении ссылки");
    }
}
