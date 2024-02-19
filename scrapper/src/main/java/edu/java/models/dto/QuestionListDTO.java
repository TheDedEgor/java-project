package edu.java.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class QuestionListDTO {

    @JsonProperty("items")
    private List<QuestionDTO> questions;

    @Getter @Setter @NoArgsConstructor
    public static class QuestionDTO {
        @JsonProperty("question_id")
        private Integer id;

        private String title;

        @JsonProperty("last_activity_date")
        private OffsetDateTime lastActivityDate;
    }
}
