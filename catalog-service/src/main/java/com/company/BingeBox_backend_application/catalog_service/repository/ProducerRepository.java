package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer,Long> {
}
