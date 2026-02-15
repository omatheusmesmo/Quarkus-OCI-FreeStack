package com.freestack.article.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.freestack.article.dto.ArticleResponse;
import com.freestack.article.dto.CreateArticleRequest;
import com.freestack.article.service.ArticleService;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService service;

    @GET
    public List<ArticleResponse> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public ArticleResponse get(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(@Valid CreateArticleRequest request) {
        ArticleResponse created = service.createArticle(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/search")
    public List<ArticleResponse> search(@QueryParam("tag") String tag) {
        return service.findByTag(tag);
    }
}
