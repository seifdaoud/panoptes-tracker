package com.panoptes.tracking.service;

import com.panoptes.tracking.entity.*;
import com.panoptes.tracking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingEventService {

    private final ContainerRepository containerRepo;
    private final TrackingEventRepository eventRepo;
    private final LocationSiteRepository siteRepo;
    private final EventTypeRepository typeRepo;

    public TrackingEvent createEvent(String containerCode, String typeName,
                                     LocalDateTime timestamp, String locationName,
                                     String metadata) {

        // Règle RM5 : le timestamp ne peut pas être dans le futur lointain
        if (timestamp.isAfter(LocalDateTime.now().plusMinutes(1))) {
            throw new IllegalArgumentException("Timestamp cannot be in the future.");
        }

        Container container = containerRepo.findByCode(containerCode)
                .orElseThrow(() -> new EntityNotFoundException("Container not found"));

        EventType type = typeRepo.findById(typeName)
                .orElseThrow(() -> new EntityNotFoundException("EventType not found"));

        // Règle RM1 : éviter doublon événement même timestamp/type
        if (eventRepo.existsByContainerAndTypeAndTimestamp(container, type, timestamp)) {
            throw new IllegalStateException("Duplicate event at same timestamp and type.");
        }

        // Règle RM2 : MOVE => location obligatoire
        LocationSite site = null;
        if ("MOVE".equalsIgnoreCase(typeName)) {
            if (locationName == null || locationName.isBlank()) {
                throw new IllegalArgumentException("Location required for MOVE events.");
            }
            site = siteRepo.findByName(locationName)
                    .orElseGet(() -> siteRepo.save(new LocationSite(null, locationName, null)));
        }

        // Création de l’événement
        TrackingEvent event = new TrackingEvent();
        event.setContainer(container);
        event.setType(type);
        event.setTimestamp(timestamp);
        event.setLocation(site);
        event.setMetadata(metadata);

        TrackingEvent saved = eventRepo.save(event);

        // Règle RM3 : ERROR => blocage conteneur
        if ("ERROR".equalsIgnoreCase(typeName)) {
            container.setStatus("BLOCKED");
            containerRepo.save(container);
        }

        // Règle RM4 : si type = CLOSE => livrable
        if ("CLOSE".equalsIgnoreCase(typeName)) {
            container.setStatus("DELIVERED");
            containerRepo.save(container);
        }

        return saved;
    }

    public List<TrackingEvent> getEventsByContainer(String containerCode) {
        return eventRepo.findByContainerCodeOrderByTimestampDesc(containerCode);
    }

    public List<TrackingEvent> filterEvents(String containerCode, String type,
                                            LocalDateTime from, LocalDateTime to, String location) {
        return eventRepo.filterEvents(containerCode, type, from, to, location);
    }

    public Page<TrackingEvent> filterEventsPaged(String containerCode, String type,
                                                 LocalDateTime from, LocalDateTime to,
                                                 String location, Pageable pageable) {
        return eventRepo.filterEventsPaged(containerCode, type, from, to, location, pageable);
    }


}
