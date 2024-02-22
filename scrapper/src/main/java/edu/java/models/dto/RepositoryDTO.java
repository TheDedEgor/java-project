package edu.java.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record RepositoryDTO(
    Long id,
    String name,
    @JsonProperty("pushed_at")
     OffsetDateTime pushedAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {

}
