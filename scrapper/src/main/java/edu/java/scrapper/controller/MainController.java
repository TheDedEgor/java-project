package edu.java.scrapper.controller;

import edu.java.scrapper.exception.ExistChatException;
import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.dto.AddLinkRequest;
import edu.java.scrapper.models.dto.LinkResponse;
import edu.java.scrapper.models.dto.ListLinksResponse;
import edu.java.scrapper.models.dto.RemoveLinkRequest;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.TgChatService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LinkService linkService;

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

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        var links = linkService.listAll(tgChatId).stream()
            .map(link -> new LinkResponse(link.id(), link.url()))
            .toList();
        var listLinksResponse = new ListLinksResponse(links, links.size());
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> createLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) throws URISyntaxException, NotFoundChatException, ExistLinkException {
        var link = linkService.add(tgChatId, new URI(addLinkRequest.link()));
        var linkResponse = new LinkResponse(link.id(), link.url());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) throws URISyntaxException, NotFoundLinkException, NotFoundChatException {
        var link = linkService.remove(tgChatId, new URI(removeLinkRequest.link()));
        var linkResponse = new LinkResponse(link.id(), link.url());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
