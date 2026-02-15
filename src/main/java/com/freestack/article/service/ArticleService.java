package com.freestack.article.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import com.freestack.article.domain.Article;
import com.freestack.article.dto.ArticleResponse;
import com.freestack.article.dto.CreateArticleRequest;
import com.freestack.article.repository.ArticleRepository;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ArticleService {

    @Inject
    ArticleRepository repository;

    @Transactional
    public ArticleResponse createArticle(CreateArticleRequest request) {
        Article article = new Article();
        article.title = request.title();
        article.author = request.author();
        article.content = request.content();
        article.createdAt = Instant.now();

        repository.persist(article);

        return mapToResponse(article);
    }

    public List<ArticleResponse> listAll() {
        return repository.listAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ArticleResponse> findByTag(String tag) {
        return repository.findByTag(tag).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ArticleResponse findById(Long id) {
        return repository.findByIdOptional(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Article not found"));
    }

    private ArticleResponse mapToResponse(Article article) {
        return new ArticleResponse(
                article.id,
                article.title,
                article.author,
                article.content,
                article.createdAt);
    }
}
