package com.panoptes.tracking.repository;

import com.panoptes.tracking.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContainerRepository extends JpaRepository<Container, Long> {
    Optional<Container> findByCode(String code);
}
