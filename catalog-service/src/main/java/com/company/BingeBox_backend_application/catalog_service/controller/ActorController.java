package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.catalog_service.dtos.ActorDto;
import com.company.BingeBox_backend_application.catalog_service.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    // --- CRUD ---

    @PostMapping
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorDto actorDto) {
        return ResponseEntity.ok(actorService.createActor(actorDto));
    }

    @GetMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    @GetMapping
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @PutMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<ActorDto> updateActor(@PathVariable Long id, @RequestBody ActorDto actorDto) {
        return ResponseEntity.ok(actorService.updateActor(id, actorDto));
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}
