package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {
}
