package com.panoptes.tracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EventType {

    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
