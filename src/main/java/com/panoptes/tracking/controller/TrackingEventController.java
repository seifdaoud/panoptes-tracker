package com.panoptes.tracking.controller;

import com.panoptes.tracking.dto.*;
import com.panoptes.tracking.entity.TrackingEvent;
import com.panoptes.tracking.mapper.TrackingEventMapper;
import com.panoptes.tracking.service.TrackingEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class TrackingEventController {

    private final TrackingEventService service;
    private final TrackingEventMapper mapper;

    @PostMapping
    public ResponseEntity<TrackingEventResponseDTO> createEvent(@Valid @RequestBody TrackingEventRequestDTO request) {
        TrackingEvent event = service.createEvent(
                request.getContainerCode(),
                request.getEventType(),
                request.getTimestamp(),
                request.getLocationName(),
                request.getMetadata()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(event));
    }

    @GetMapping("/{containerCode}")
    public ResponseEntity<List<TrackingEventResponseDTO>> getEventsByContainer(@PathVariable String containerCode) {
        List<TrackingEvent> events = service.getEventsByContainer(containerCode);
        List<TrackingEventResponseDTO> dtos = events.stream().map(mapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{containerCode}/filter")
    public ResponseEntity<List<TrackingEventResponseDTO>> filterEvents(
            @PathVariable String containerCode,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) String location) {

        List<TrackingEvent> events = service.filterEvents(containerCode, type, from, to, location);
        return ResponseEntity.ok(events.stream().map(mapper::toDTO).toList());
    }

    @GetMapping("/{containerCode}/filter-paged")
    public ResponseEntity<Page<TrackingEventResponseDTO>> filterPaged(
            @PathVariable String containerCode,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp,desc") String[] sort) {

        Sort sortObj = Sort.by(Arrays.stream(sort)
                .map(s -> {
                    String[] parts = s.split(",");
                    return parts.length == 2 && parts[1].equalsIgnoreCase("desc")
                            ? Sort.Order.desc(parts[0])
                            : Sort.Order.asc(parts[0]);
                }).toList());

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<TrackingEvent> result = service.filterEventsPaged(containerCode, type, from, to, location, pageable);

        Page<TrackingEventResponseDTO> dtoPage = result.map(mapper::toDTO);

        return ResponseEntity.ok(dtoPage);
    }


}
