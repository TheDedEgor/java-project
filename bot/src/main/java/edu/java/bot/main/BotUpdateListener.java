package edu.java.bot.main;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.commands.BotCommand;
import edu.java.bot.commands.DefaultCommand;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.List;
import java.util.regex.Pattern;

public class BotUpdateListener implements UpdatesListener {

    private final TelegramBot bot;

    private static final Pattern TRACK_PATTERN = Pattern.compile("/track\\s+\\S+");

    private static final Pattern UNTRACK_PATTERN = Pattern.compile("/untrack\\s+\\S+");

    public BotUpdateListener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(this::handleUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void handleUpdate(Update update) {
        var messageText = update.message().text().trim();
        BotCommand command = switch (messageText) {
            case "/start" -> new StartCommand();
            case "/help" -> new HelpCommand();
            case String s when TRACK_PATTERN.matcher(s).matches() -> new TrackCommand();
            case String s when UNTRACK_PATTERN.matcher(s).matches() -> new UntrackCommand();
            case "/list" -> new ListCommand();
            default -> new DefaultCommand();
        };

        var sendMessage = command.handle(update);
        SendResponse response = bot.execute(sendMessage);
    }
}
