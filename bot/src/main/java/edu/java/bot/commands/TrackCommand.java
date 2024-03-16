package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.links.LinkRepository;
import edu.java.bot.repository.links.LinkValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@SuppressWarnings("MagicNumber")
public class TrackCommand implements BotCommand {

    @Autowired
    @Qualifier("InDbLinkRepository")
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
            try {
                linkRepository.trackLink(chatId, url);
                return new SendMessage(chatId, "Ссылка успешно добавлена");
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().value() == 400) {
                    return new SendMessage(chatId, "Ссылка уже добавлена");
                } else if (ex.getStatusCode().value() == 404) {
                    return new SendMessage(chatId, "Вы не были еще зарегистрированы");
                }
                return new SendMessage(chatId, "Не удалось добавить ссылку");
            }
        } else {
            return new SendMessage(chatId, "Неверный формат ссылки");
        }
    }
}
