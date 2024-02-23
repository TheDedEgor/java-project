package edu.java.bot.models.dto;

import java.net.URI;

public record LinkResponseDTO(
    Integer id,
    URI url
) {
}
