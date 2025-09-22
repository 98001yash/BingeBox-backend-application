package com.company.BingeBox_backend_application.catalog_service.entity;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tv_shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TVShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String thumbnailUrl;
    private String trailerUrl;

    @ElementCollection
    @CollectionTable(name = "tvshow_genres", joinColumns = @JoinColumn(name = "tvshow_id"))
    @Column(name = "genre")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "tvshow_cast", joinColumns = @JoinColumn(name = "tvshow_id"))
    @Column(name = "actor")
    private List<String> cast;

    private String maturityRating;

    private boolean featured = false;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Season> seasons;
}

