package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.MetricCounter;
import edu.java.bot.models.dto.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    @Autowired
    private TelegramBot bot;

    @Autowired
    private MetricCounter metricCounter;

    public void updatesHandler(UpdateRequest updateRequest) {
        var ids = updateRequest.tgChatIds();
        for (var id : ids) {
            var message = new SendMessage(
                id,
                "Обновление по ссылке: " + updateRequest.url() + "\n" + "Описание: " + updateRequest.description()
            );
            bot.execute(message);
        }
        metricCounter.increment();
    }
}
