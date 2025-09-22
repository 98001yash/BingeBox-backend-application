package com.company.BingeBox_backend_application.catalog_service.entity;



import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileImageUrl;

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies;

    @ManyToMany(mappedBy = "directors")
    private Set<TVShow> tvShows;
}
