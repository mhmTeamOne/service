package com.mhm.repositories;

import com.mhm.entities.ServiceCategory;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ServiceCategoryRepository implements PanacheRepository<ServiceCategory> {

    // Save a ServiceCategory
    public Uni<ServiceCategory> createServiceCategory(ServiceCategory serviceCategory) {
        return persist(serviceCategory);
    }

    // Find a ServiceCategory by ID
    public Uni<ServiceCategory> findServiceCategoryById(Long id) {
        return findById(id);
    }

    // Find a ServiceCategory by identifier
    public Uni<ServiceCategory> findServiceCategoryByIdentifier(String identifier) {
        return find("identifier", identifier).firstResult();
    }

    // Find active service categories for a country
    public Uni<List<ServiceCategory>> findActiveServiceCategoriesByCountry(Long countryId) {
        return find("country.id = ?1 and active = ?2", countryId, true).list();
    }

    // Find all service categories for a country
    public Uni<List<ServiceCategory>> findServiceCategoriesByCountry(Long countryId) {
        return find("country.id", countryId).list();
    }

    // Find all active service categories
    public Uni<List<ServiceCategory>> findActiveServiceCategories() {
        return find("active", true).list();
    }

    // Find all service categories
    public Uni<List<ServiceCategory>> findAllServiceCategories() {
        return listAll();
    }

    // Update a ServiceCategory
    public Uni<ServiceCategory> updateServiceCategory(ServiceCategory serviceCategory) {
        return persist(serviceCategory);
    }

    // Delete a ServiceCategory by ID
    public Uni<Boolean> deleteServiceCategoryById(Long id) {
        return deleteById(id);
    }
}