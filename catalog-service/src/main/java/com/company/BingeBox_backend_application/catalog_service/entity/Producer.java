package com.company.BingeBox_backend_application.catalog_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "producers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileImageUrl;

    @ManyToMany(mappedBy = "producers")
    private Set<Movie> movies;

    @ManyToMany(mappedBy = "producers")
    private Set<TVShow> tvShows;
}
