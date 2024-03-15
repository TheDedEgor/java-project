package edu.java.scrapper.models.dto;

import java.util.List;

public record ErrorResponse(
    Integer code,
    String description,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
}
