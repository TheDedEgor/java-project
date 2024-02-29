package edu.java.bot.models.dto;

import java.net.URI;

public record LinkResponse(
    Integer id,
    URI url
) {
}
