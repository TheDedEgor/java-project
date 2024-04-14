package edu.java.bot.controller;

import edu.java.bot.models.dto.UpdateRequest;
import edu.java.bot.service.UpdateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesController {

    @Autowired
    private UpdateService updateService;

    @PostMapping("/updates")
    public ResponseEntity<?> updates(@RequestBody @Valid UpdateRequest updateRequest) {
        updateService.updatesHandler(updateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
