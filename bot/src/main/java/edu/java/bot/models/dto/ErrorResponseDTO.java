package edu.java.bot.models.dto;

import java.util.List;

public record ErrorResponseDTO(
    String code,
    String description,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}
