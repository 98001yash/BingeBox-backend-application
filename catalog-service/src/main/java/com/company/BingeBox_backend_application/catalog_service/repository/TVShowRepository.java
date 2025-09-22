package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVShowRepository extends JpaRepository<TVShow,Long> {
}
