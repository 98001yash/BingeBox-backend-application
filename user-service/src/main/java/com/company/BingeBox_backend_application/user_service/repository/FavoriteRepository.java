package com.company.BingeBox_backend_application.user_service.repository;

import com.company.BingeBox_backend_application.user_service.entities.FavoriteItem;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteItem, Long> {
    List<FavoriteItem> findByUser(UserProfile user);

    Optional<FavoriteItem> findByUserAndContentIdAndContentType(UserProfile user, Long contentId, String contentType);

    boolean existsByUserAndContentIdAndContentType(UserProfile user, Long contentId, String contentType);

}