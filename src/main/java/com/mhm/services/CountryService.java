package com.mhm.services;

import com.mhm.entities.Country;
import com.mhm.entities.EmergencyContact;
import com.mhm.repositories.CountryRepository;
import com.mhm.repositories.EmergencyContactRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CountryService {

    @Inject
    CountryRepository countryRepository;

    @Inject
    EmergencyContactRepository emergencyContactRepository;

    /**
     * Create a new country
     */
    public Uni<Country> createCountry(Country country) {
        return countryRepository.createCountry(country);
    }

    /**
     * Update an existing country
     */
    public Uni<Country> updateCountry(Long id, Country country) {
        return countryRepository.findCountryById(id)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found with id: " + id))
                .onItem().transformToUni(existingCountry -> {
                    country.id = id;
                    return countryRepository.updateCountry(country);
                });
    }

    /**
     * Get country by ID
     */
    public Uni<Country> getCountryById(Long id) {
        return countryRepository.findCountryById(id)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found with id: " + id));
    }

    /**
     * Get country by code
     */
    public Uni<Country> getCountryByCode(String code) {
        return countryRepository.findCountryByCode(code)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found with code: " + code));
    }

    /**
     * Get all countries
     */
    public Uni<List<Country>> getAllCountries() {
        return countryRepository.findAllCountries();
    }

    /**
     * Get supported countries
     */
    public Uni<List<Country>> getSupportedCountries() {
        return countryRepository.findSupportedCountries();
    }

    /**
     * Delete country by ID
     */
    public Uni<Boolean> deleteCountry(Long id) {
        return countryRepository.findCountryById(id)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found with id: " + id))
                .onItem().transformToUni(country -> countryRepository.deleteCountryById(id));
    }

    /**
     * Get emergency contacts for a country
     */
    public Uni<List<EmergencyContact>> getEmergencyContactsByCountry(Long countryId) {
        return emergencyContactRepository.findEmergencyContactsByCountry(countryId);
    }

    /**
     * Get emergency contact by country and type
     */
    public Uni<EmergencyContact> getEmergencyContactByCountryAndType(Long countryId, String type) {
        return emergencyContactRepository.findEmergencyContactByCountryAndType(countryId, type)
                .onItem().ifNull().failWith(() -> new RuntimeException("Emergency contact not found for type: " + type));
    }
}