package com.panoptes.tracking.repository;

import com.panoptes.tracking.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, String> {
}
