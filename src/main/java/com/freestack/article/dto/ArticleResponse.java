package com.freestack.article.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;

public record ArticleResponse(
                Long id,
                String title,
                String author,
                JsonNode content,
                Instant createdAt) {
}
