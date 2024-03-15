package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@SuppressWarnings("MagicNumber")
public class DeleteCommand implements BotCommand {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public String command() {
        return "/delete";
    }

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        try {
            chatRepository.unregisterChat(chatId);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 404) {
                return new SendMessage(chatId, "Вы не были еще зарегистрированы");
            } else {
                return new SendMessage(chatId, "Произошла неизвестная ошибка");
            }
        }
        return new SendMessage(chatId, "Аккаунт успешно удален");
    }
}
