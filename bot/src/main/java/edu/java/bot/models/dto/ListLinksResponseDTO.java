package edu.java.bot.models.dto;

import java.util.List;

public record ListLinksResponseDTO(
    List<LinkResponseDTO> links,
    Integer size
) {
}
