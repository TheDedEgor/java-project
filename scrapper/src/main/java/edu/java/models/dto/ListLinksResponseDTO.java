package edu.java.models.dto;

import java.util.List;

public record ListLinksResponseDTO(
    List<LinkResponseDTO> links,
    Integer size
) {
}
