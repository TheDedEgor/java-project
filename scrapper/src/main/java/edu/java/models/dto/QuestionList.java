package edu.java.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionList(
    @JsonProperty("items")
    List<QuestionDTO> questions

) {
    public record QuestionDTO(
        @JsonProperty("question_id")
        Integer id,
        String title,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {

    }
}
