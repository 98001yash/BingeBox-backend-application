package com.company.BingeBox_backend_application.user_service.repository;

import com.company.BingeBox_backend_application.user_service.entities.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<WatchlistItem,Long> {

    List<WatchlistItem> findByUser_UserId(Long userId);
    Optional<WatchlistItem> findByUser_UserIdAndContentIdAndContentType(Long userId, Long contentId, String contentType);
    boolean existsByUser_UserIdAndContentIdAndContentType(Long userId, Long contentId, String contentType);

}
