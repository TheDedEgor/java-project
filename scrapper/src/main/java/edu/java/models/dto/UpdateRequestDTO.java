package edu.java.models.dto;

import java.util.List;

public record UpdateRequestDTO(
    Integer id,
    String url,
    String description,
    List<Integer> tgChatIds
) {
}
