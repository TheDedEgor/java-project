package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.links.LinkRepository;
import edu.java.bot.links.LinkValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TrackCommand implements BotCommand {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkValidation linkValidation;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    @SuppressWarnings("ReturnCount")
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var msg = update.message().text().split("\\s+");
        if (msg.length < 2) {
            return new SendMessage(chatId, "Введите ссылку после команды");
        }
        var url = msg[1];
        if (linkValidation.validateLink(url)) {
            if (linkRepository.existLink(chatId, url)) {
                return new SendMessage(chatId, "Ссылка уже добавлена");
            }
            var added = linkRepository.trackLink(chatId, url);
            if (added) {
                return new SendMessage(chatId, "Ссылка успешно добавлена");
            }
            return new SendMessage(chatId, "Не удалось добавить ссылку");
        } else {
            return new SendMessage(chatId, "Неверный формат ссылки");
        }
    }
}
