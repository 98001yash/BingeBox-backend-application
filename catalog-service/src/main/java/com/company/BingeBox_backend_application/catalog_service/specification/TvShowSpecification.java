package com.company.BingeBox_backend_application.catalog_service.specification;

import com.company.BingeBox_backend_application.catalog_service.entity.Actor;
import com.company.BingeBox_backend_application.catalog_service.entity.Genre;
import com.company.BingeBox_backend_application.catalog_service.entity.TVShow;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TvShowSpecification {

    public static Sort filterBy(
            String title,
            Set<Long> genreIds,
            Set<Long> actorIds,
            Set<Long> directorIds,
            Set<Long> producerIds,
            Long categoryId,
            Boolean featured
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (genreIds != null && !genreIds.isEmpty()) {
                Join<TVShow, Genre> genres = root.join("genres");
                predicates.add(genres.get("id").in(genreIds));
            }
            if (actorIds != null && !actorIds.isEmpty()) {
                Join<TVShow, Actor> actors = root.join("actors");
                predicates.add(actors.get("id").in(actorIds));
            }
            if (directorIds != null && !directorIds.isEmpty()) {
                Join<TVShow, com.company.BingeBox_backend_application.catalog_service.entity.Director> directors = root.join("directors");
                predicates.add(directors.get("id").in(directorIds));
            }
            if (producerIds != null && !producerIds.isEmpty()) {
                Join<TVShow, com.company.BingeBox_backend_application.catalog_service.entity.Producer> producers = root.join("producers");
                predicates.add(producers.get("id").in(producerIds));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (featured != null) {
                predicates.add(cb.equal(root.get("featured"), featured));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}