package edu.java.client;

import edu.java.models.dto.QuestionListDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowClient {
    @GetExchange("/questions/{id}?order=desc&sort=activity&site=stackoverflow")
    QuestionListDTO getQuestion(@PathVariable Long id);
}
