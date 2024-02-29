package edu.java.client;

import edu.java.models.dto.QuestionList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowClient {
    @GetExchange("/questions/{id}?order=desc&sort=activity&site=stackoverflow")
    QuestionList getQuestion(@PathVariable Long id);
}
