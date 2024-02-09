package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TrackCommand implements BotCommand {

    private static final Pattern URL_PATTERN = Pattern.compile(
        "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    // TODO имитация данных в бд
    public static Map<Long, List<String>> mapUrl = new HashMap<>();

    @Override
    public SendMessage handle(Update update) {
        var chatId = update.message().chat().id();
        var msg = update.message().text();
        var url = msg.split("\\s+")[1];
        if (URL_PATTERN.matcher(url).matches()) {
            var text = "Ссылка успешно добавлена";
            var urls = mapUrl.getOrDefault(chatId, new ArrayList<>());
            if (urls.isEmpty()) {
                urls.add(url);
                mapUrl.put(chatId, urls);
                return new SendMessage(chatId, text);
            }
            if (!urls.contains(url)) {
                urls.add(url);
                return new SendMessage(chatId, text);
            }
            return new SendMessage(chatId, "Ссылка уже добавлена");
        } else {
            return new SendMessage(chatId, "Неверный формат ссылки");
        }
    }
}
