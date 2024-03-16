package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@SuppressWarnings("MagicNumber")
public class StartCommand implements BotCommand {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        try {
            chatRepository.registerChat(chatId);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 400) {
                return new SendMessage(chatId, "Вы уже зарегистрированы");
            } else {
                return new SendMessage(chatId, "Произошла неизвестная ошибка");
            }
        }
        return new SendMessage(chatId, "Вы успешно зарегистрированы");
    }
}
