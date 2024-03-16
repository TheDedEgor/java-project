package edu.java.scrapper.models.domain;

import java.time.OffsetDateTime;

public record Link(Long id, String url, OffsetDateTime lastCheckTime) {
}
