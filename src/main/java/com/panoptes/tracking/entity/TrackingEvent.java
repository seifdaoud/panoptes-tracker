package com.panoptes.tracking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TrackingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Container container;

    @ManyToOne(optional = false)
    private EventType type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    private LocationSite location;

    private String metadata;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocationSite getLocation() {
        return location;
    }

    public void setLocation(LocationSite location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
