package edu.java.bot.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.BotCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotUpdateListener implements UpdatesListener {

    private final TelegramBot bot;

    private final Map<String, BotCommand> commands = new HashMap<>();

    @Autowired
    public BotUpdateListener(TelegramBot bot, List<BotCommand> commands) {
        this.bot = bot;
        for (var command : commands) {
            this.commands.put(command.command(), command);
        }
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(this::handleUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void handleUpdate(Update update) {
        var chatId = update.message().chat().id();
        var messageText = update.message().text().trim().toLowerCase();
        var commandText = messageText.split("\\s+")[0];

        var command = commands.getOrDefault(commandText, commands.get("/default"));

        try {
            var sendMessage = command.handle(update);
            bot.execute(sendMessage);
        } catch (Exception ex) {
            var sendMessage = new SendMessage(chatId, "Во время выполнения команды произошла ошибка");
            bot.execute(sendMessage);
        }
    }
}
