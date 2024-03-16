package edu.java.scrapper.controller;

import edu.java.scrapper.exception.ExistLinkException;
import edu.java.scrapper.exception.NotFoundChatException;
import edu.java.scrapper.exception.NotFoundLinkException;
import edu.java.scrapper.models.dto.AddLinkRequest;
import edu.java.scrapper.models.dto.LinkResponse;
import edu.java.scrapper.models.dto.ListLinksResponse;
import edu.java.scrapper.models.dto.RemoveLinkRequest;
import edu.java.scrapper.service.LinkService;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) throws NotFoundChatException {
        var links = linkService.listAll(tgChatId).stream()
            .map(link -> new LinkResponse(link.id(), link.url()))
            .toList();
        return new ListLinksResponse(links, links.size());
    }

    @PostMapping("/links")
    public LinkResponse createLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) throws URISyntaxException, NotFoundChatException, ExistLinkException {
        var link = linkService.add(tgChatId, new URI(addLinkRequest.link()));
        return new LinkResponse(link.id(), link.url());
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) throws URISyntaxException, NotFoundLinkException, NotFoundChatException {
        var link = linkService.remove(tgChatId, new URI(removeLinkRequest.link()));
        return new LinkResponse(link.id(), link.url());
    }
}
