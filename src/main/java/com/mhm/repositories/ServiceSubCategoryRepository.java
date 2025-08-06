package com.mhm.repositories;

import com.mhm.entities.ServiceSubCategory;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ServiceSubCategoryRepository implements PanacheRepository<ServiceSubCategory> {

    // Save a ServiceSubCategory
    public Uni<ServiceSubCategory> createServiceSubCategory(ServiceSubCategory serviceSubCategory) {
        return persist(serviceSubCategory);
    }

    // Find a ServiceSubCategory by ID
    public Uni<ServiceSubCategory> findServiceSubCategoryById(Long id) {
        return findById(id);
    }

    // Find a ServiceSubCategory by identifier
    public Uni<ServiceSubCategory> findServiceSubCategoryByIdentifier(String identifier) {
        return find("identifier", identifier).firstResult();
    }

    // Find active subcategories for a service category
    public Uni<List<ServiceSubCategory>> findActiveSubCategoriesByServiceCategory(Long serviceCategoryId) {
        return find("serviceCategory.id = ?1 and active = ?2", serviceCategoryId, true).list();
    }

    // Find all subcategories for a service category
    public Uni<List<ServiceSubCategory>> findSubCategoriesByServiceCategory(Long serviceCategoryId) {
        return find("serviceCategory.id", serviceCategoryId).list();
    }

    // Find all active subcategories
    public Uni<List<ServiceSubCategory>> findActiveSubCategories() {
        return find("active", true).list();
    }

    // Find all subcategories
    public Uni<List<ServiceSubCategory>> findAllSubCategories() {
        return listAll();
    }

    // Update a ServiceSubCategory
    public Uni<ServiceSubCategory> updateServiceSubCategory(ServiceSubCategory serviceSubCategory) {
        return persist(serviceSubCategory);
    }

    // Delete a ServiceSubCategory by ID
    public Uni<Boolean> deleteServiceSubCategoryById(Long id) {
        return deleteById(id);
    }
}