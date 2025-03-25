package com.panoptes.tracking.mapper;

import com.panoptes.tracking.dto.TrackingEventResponseDTO;
import com.panoptes.tracking.entity.TrackingEvent;
import org.springframework.stereotype.Component;

@Component
public class TrackingEventMapper {

    public TrackingEventResponseDTO toDTO(TrackingEvent event) {
        TrackingEventResponseDTO dto = new TrackingEventResponseDTO();
        dto.setId(event.getId());
        dto.setContainerCode(event.getContainer().getCode());
        dto.setEventType(event.getType().getName());
        dto.setTimestamp(event.getTimestamp());
        dto.setLocationName(event.getLocation() != null ? event.getLocation().getName() : null);
        dto.setMetadata(event.getMetadata());
        return dto;
    }
}
