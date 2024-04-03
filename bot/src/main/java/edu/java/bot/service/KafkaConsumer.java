package edu.java.bot.service;

import edu.java.bot.models.dto.UpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private MainService mainService;

    @RetryableTopic(attempts = "1", kafkaTemplate = "retryableTopicKafkaTemplate", dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${app.topic-name}", containerFactory = "containerFactory")
    public void kafka(UpdateRequest updateRequest) {
        mainService.updatesHandler(updateRequest);
    }
}
