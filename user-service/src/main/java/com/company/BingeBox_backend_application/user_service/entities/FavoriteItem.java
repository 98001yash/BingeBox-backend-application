package com.company.BingeBox_backend_application.user_service.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "favorite_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contentId;
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;
}
