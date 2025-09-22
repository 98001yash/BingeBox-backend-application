package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
