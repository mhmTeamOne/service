package com.mhm.repositories;

import com.mhm.entities.EmergencyContact;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EmergencyContactRepository implements PanacheRepository<EmergencyContact> {

    // Save an EmergencyContact
    public Uni<EmergencyContact> createEmergencyContact(EmergencyContact emergencyContact) {
        return persist(emergencyContact);
    }

    // Find an EmergencyContact by ID
    public Uni<EmergencyContact> findEmergencyContactById(Long id) {
        return findById(id);
    }

    // Find emergency contacts by country
    public Uni<List<EmergencyContact>> findEmergencyContactsByCountry(Long countryId) {
        return find("country.id", countryId).list();
    }

    // Find emergency contact by country and type
    public Uni<EmergencyContact> findEmergencyContactByCountryAndType(Long countryId, String type) {
        return find("country.id = ?1 and type = ?2", countryId, type).firstResult();
    }

    // Find all emergency contacts
    public Uni<List<EmergencyContact>> findAllEmergencyContacts() {
        return listAll();
    }

    // Update an EmergencyContact
    public Uni<EmergencyContact> updateEmergencyContact(EmergencyContact emergencyContact) {
        return persist(emergencyContact);
    }

    // Delete an EmergencyContact by ID
    public Uni<Boolean> deleteEmergencyContactById(Long id) {
        return deleteById(id);
    }
}