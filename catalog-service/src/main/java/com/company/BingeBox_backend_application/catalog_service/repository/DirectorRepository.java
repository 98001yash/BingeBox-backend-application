package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director,Long> {
}
