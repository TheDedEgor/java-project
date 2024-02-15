package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.links.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements BotCommand {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var urls = linkRepository.getLinks(chatId);
        if (urls.isEmpty()) {
            return new SendMessage(chatId, "Список ссылок пуст");
        }
        return new SendMessage(chatId, "Список отслеживаемых ссылок:\n" + String.join("\n", urls));
    }
}
