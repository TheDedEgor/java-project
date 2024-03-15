package edu.java.scrapper.models.dto;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    Integer size
) {
}
