package com.freestack.article.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateArticleRequest(
                @NotBlank String title,
                @NotBlank String author,
                @NotNull JsonNode content) {
}
