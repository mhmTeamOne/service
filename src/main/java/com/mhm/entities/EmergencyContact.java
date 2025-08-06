package com.mhm.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "emergency_contacts")
public class EmergencyContact extends PanacheEntity {
    
    @Column(name = "type", columnDefinition = "VARCHAR(20) NOT NULL")
    private String type; // general, police, medical, fire
    
    @Column(name = "number", columnDefinition = "VARCHAR(20) NOT NULL")
    private String number;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // Many-to-one relationship with country
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
}