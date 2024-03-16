package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.links.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@SuppressWarnings("MagicNumber")
public class UntrackCommand implements BotCommand {

    @Autowired
    @Qualifier("InDbLinkRepository")
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
        try {
            linkRepository.untrackLink(chatId, url);
            return new SendMessage(chatId, "Ссылка успешно удалена");
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 404) {
                return new SendMessage(chatId, "Такой ссылки не зарегистрировано");
            }
            return new SendMessage(chatId, "Произошла ошибка при удалении ссылки");
        }
    }
}
