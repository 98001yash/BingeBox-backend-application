package com.company.BingeBox_backend_application.user_service.entities;


import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "watchlist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contentId;   // movieId or tvShowId from Catalog
    private String contentType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;
}
