package edu.java.bot.client;

import edu.java.bot.models.dto.AddLinkRequestDTO;
import edu.java.bot.models.dto.LinkResponseDTO;
import edu.java.bot.models.dto.ListLinksResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @PostExchange("/tg-chat/{id}")
    void createTgChat(@PathVariable Integer id);

    @DeleteExchange("/tg-chat/{id}")
    void deleteTgChat(@PathVariable Integer id);

    @GetExchange("/links")
    ListLinksResponseDTO getLinks(@RequestHeader("Tg-Chat-Id") Integer chatId);

    @PostExchange("/links")
    LinkResponseDTO createLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody AddLinkRequestDTO addLinkRequestDTO
    );

    @DeleteExchange("/links")
    LinkResponseDTO deleteLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody AddLinkRequestDTO addLinkRequestDTO
    );
}
