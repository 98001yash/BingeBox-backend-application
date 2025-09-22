package com.company.BingeBox_backend_application.catalog_service.repository;

import com.company.BingeBox_backend_application.catalog_service.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {
}
