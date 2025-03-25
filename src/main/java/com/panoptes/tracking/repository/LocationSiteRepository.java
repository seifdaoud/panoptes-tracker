package com.panoptes.tracking.repository;

import com.panoptes.tracking.entity.LocationSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationSiteRepository extends JpaRepository<LocationSite, Long> {
    Optional<LocationSite> findByName(String name);
}
