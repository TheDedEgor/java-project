package edu.java.scrapper.models.entity;

import java.time.OffsetDateTime;

public record Link(Long id, String url, OffsetDateTime lastCheckTime) {
}
