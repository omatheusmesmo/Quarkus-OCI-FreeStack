package com.freestack.article.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import com.freestack.article.domain.Article;

@ApplicationScoped
public class ArticleRepository implements PanacheRepository<Article> {

    public List<Article> findByTag(String tag) {
        return getEntityManager()
                .createNativeQuery(
                        "SELECT * FROM Article WHERE json_exists(content, '$.tags?(@ == $t)' PASSING :tag AS \"t\")",
                        Article.class)
                .setParameter("tag", tag)
                .getResultList();
    }
}
