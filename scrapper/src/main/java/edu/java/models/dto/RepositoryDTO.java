package edu.java.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RepositoryDTO {
    private Long id;
    private String name;

    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
