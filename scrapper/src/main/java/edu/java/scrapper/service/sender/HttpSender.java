package edu.java.scrapper.service.sender;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.models.dto.UpdateRequest;
import edu.java.scrapper.service.SenderNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpSender implements SenderNotification {

    @Autowired
    private BotClient botClient;

    @Override
    public void send(UpdateRequest updateRequest) {
        botClient.updates(updateRequest);
    }
}
