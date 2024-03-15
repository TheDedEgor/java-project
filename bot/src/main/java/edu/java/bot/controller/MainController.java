package edu.java.bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.dto.UpdateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private TelegramBot bot;

    @PostMapping("/updates")
    public ResponseEntity<?> updates(@RequestBody @Valid UpdateRequest updateRequest) {
        var ids = updateRequest.tgChatIds();
        for (var id : ids) {
            var message = new SendMessage(
                id,
                "Обновление по ссылке: " + updateRequest.url() + "\n" + "Описание: " + updateRequest.description()
            );
            bot.execute(message);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
