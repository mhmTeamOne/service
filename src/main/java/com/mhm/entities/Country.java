package com.mhm.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "countries")
public class Country extends PanacheEntity {
    
    @Column(name = "code", columnDefinition = "VARCHAR(10) UNIQUE NOT NULL")
    private String code;
    
    @Column(name = "name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;
    
    @Column(name = "flag", columnDefinition = "VARCHAR(10)")
    private String flag;
    
    @Column(name = "currency", columnDefinition = "VARCHAR(10)")
    private String currency;
    
    @Column(name = "timezone", columnDefinition = "VARCHAR(50)")
    private String timezone;
    
    @Column(name = "is_supported", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isSupported = false;
    
    // One-to-many relationship with emergency contacts
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts;
    
    // One-to-many relationship with services
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceCategory> services;
}