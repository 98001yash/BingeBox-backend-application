package com.company.BingeBox_backend_application.user_service.repository;

import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import com.company.BingeBox_backend_application.user_service.entities.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<WatchlistItem,Long> {

    List<WatchlistItem> findByUser(UserProfile user);

    Optional<WatchlistItem> findByUserAndContentIdAndContentType(UserProfile user, Long contentId, String contentType);
    boolean existsByUserAndContentIdAndContentType(UserProfile user,  Long contentId, String contentType);
}
