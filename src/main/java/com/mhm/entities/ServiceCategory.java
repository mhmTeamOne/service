package com.mhm.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "service_categories")
public class ServiceCategory extends PanacheEntity {
    
    @Column(name = "external_id", columnDefinition = "INTEGER")
    private Integer externalId; // The "id" from JSON
    
    @Column(name = "name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;
    
    @Column(name = "identifier", columnDefinition = "VARCHAR(100) UNIQUE NOT NULL")
    private String identifier;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;
    
    // Many-to-one relationship with country
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
    
    // One-to-many relationship with subcategories
    @OneToMany(mappedBy = "serviceCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceSubCategory> subCategories;
}