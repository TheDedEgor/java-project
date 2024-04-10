package edu.java.scrapper.service.sender;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.models.dto.UpdateRequest;
import edu.java.scrapper.service.SenderNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScrapperQueueProducer implements SenderNotification {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private KafkaTemplate<String, UpdateRequest> template;

    public void send(UpdateRequest updateRequest) {
        template.send(applicationConfig.topicName(), updateRequest);
    }
}
