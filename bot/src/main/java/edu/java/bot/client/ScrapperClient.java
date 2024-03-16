package edu.java.bot.client;

import edu.java.bot.models.dto.AddLinkRequest;
import edu.java.bot.models.dto.LinkResponse;
import edu.java.bot.models.dto.ListLinksResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("/tg-chat/{id}")
    void createTgChat(@PathVariable Long id);

    @DeleteExchange("/tg-chat/{id}")
    void deleteTgChat(@PathVariable Long id);

    @GetExchange("/links")
    ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long chatId);

    @PostExchange("/links")
    LinkResponse createLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody AddLinkRequest addLinkRequest
    );

    @DeleteExchange("/links")
    LinkResponse deleteLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody AddLinkRequest addLinkRequest
    );
}
