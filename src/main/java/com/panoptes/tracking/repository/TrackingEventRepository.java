package com.panoptes.tracking.repository;

import com.panoptes.tracking.entity.Container;
import com.panoptes.tracking.entity.EventType;
import com.panoptes.tracking.entity.TrackingEvent;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {
    List<TrackingEvent> findByContainerCodeOrderByTimestampDesc(String containerCode);

    boolean existsByContainerAndTypeAndTimestamp(Container container, EventType type, LocalDateTime timestamp);

    List<TrackingEvent> filterEvents(String containerCode, String type, LocalDateTime from, LocalDateTime to, String location);

    Page<TrackingEvent> findByContainer_Code(String containerCode, Pageable pageable);

    Page<TrackingEvent> filterEventsPaged(String containerCode, String type, LocalDateTime from, LocalDateTime to, String location, Pageable pageable);
}

