package com.company.BingeBox_backend_application.user_service.repository;

import com.company.BingeBox_backend_application.user_service.entities.FavoriteItem;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteItem, Long> {
    // Fetch all favorite items for a user
    List<FavoriteItem> findByUser_UserId(Long userId);

    // Find a specific favorite item by user + content
    Optional<FavoriteItem> findByUser_UserIdAndContentIdAndContentType(Long userId, Long contentId, String contentType);

    // Check if a content already exists in favorites
    boolean existsByUser_UserIdAndContentIdAndContentType(Long userId, Long contentId, String contentType);


}