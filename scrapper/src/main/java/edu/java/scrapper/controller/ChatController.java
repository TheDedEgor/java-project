package edu.java.scrapper.controller;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.service.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private TgChatService tgChatService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> createTgChat(@PathVariable Long id) throws ExistChatException {
        tgChatService.register(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteTgChat(@PathVariable Long id) throws NotFoundChatException {
        tgChatService.unregister(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
