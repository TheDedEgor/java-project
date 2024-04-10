package edu.java.scrapper.service;

import edu.java.scrapper.models.dto.UpdateRequest;

public interface SenderNotification {
    void send(UpdateRequest updateRequest);
}
