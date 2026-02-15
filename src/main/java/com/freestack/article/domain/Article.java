package com.freestack.article.domain;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
public class Article extends PanacheEntity {

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String author;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    public JsonNode content;

    @Column(nullable = false, updatable = false)
    public Instant createdAt;
}
