package edu.java.client;

import edu.java.models.dto.UpdateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {
    @PostExchange("/updates")
    void updates(@RequestBody UpdateRequest updateRequest);
}
