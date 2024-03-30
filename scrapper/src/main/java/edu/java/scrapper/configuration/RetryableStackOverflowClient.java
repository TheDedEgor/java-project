package edu.java.scrapper.configuration;

import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.models.dto.QuestionList;
import org.springframework.retry.support.RetryTemplate;

public class RetryableStackOverflowClient implements StackOverflowClient {

    private final RetryTemplate retryTemplate;
    private final StackOverflowClient stackOverflowClient;

    public RetryableStackOverflowClient(RetryTemplate retryTemplate, StackOverflowClient stackOverflowClient) {
        this.retryTemplate = retryTemplate;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public QuestionList getQuestion(Long id) {
        return retryTemplate.execute((context -> stackOverflowClient.getQuestion(id)));
    }
}
