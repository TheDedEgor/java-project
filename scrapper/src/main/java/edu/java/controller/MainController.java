package edu.java.controller;

import edu.java.models.dto.AddLinkRequestDTO;
import edu.java.models.dto.LinkResponseDTO;
import edu.java.models.dto.ListLinksResponseDTO;
import edu.java.models.dto.RemoveLinkRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> createTgChat(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteTgChat(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponseDTO> getLinks(@RequestHeader("Tg-Chat-Id") Integer chatId) {
        var listLinksResponseDTO =
            new ListLinksResponseDTO(List.of(new LinkResponseDTO(1, "https://edu.tinkoff.ru")), 1);
        return new ResponseEntity<>(listLinksResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponseDTO> createLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid AddLinkRequestDTO addLinkRequestDTO
    ) {
        var linkResponseDTO = new LinkResponseDTO(1, "https://github.com");
        return new ResponseEntity<>(linkResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponseDTO> deleteLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid RemoveLinkRequestDTO removeLinkRequestDTO
    ) {
        var linkResponseDTO = new LinkResponseDTO(1, "https://stackoverflow.com/");
        return new ResponseEntity<>(linkResponseDTO, HttpStatus.OK);
    }
}
