package edu.java.scrapper.client;

import edu.java.scrapper.models.dto.UpdateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {
    @PostExchange("/updates")
    void updates(@RequestBody UpdateRequest updateRequest);
}
