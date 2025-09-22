package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
