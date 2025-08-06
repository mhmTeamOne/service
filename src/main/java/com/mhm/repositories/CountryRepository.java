package com.mhm.repositories;

import com.mhm.entities.Country;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<Country> {

    // Save a Country
    public Uni<Country> createCountry(Country country) {
        return persist(country);
    }

    // Find a Country by ID
    public Uni<Country> findCountryById(Long id) {
        return findById(id);
    }

    // Find a Country by code
    public Uni<Country> findCountryByCode(String code) {
        return find("code", code).firstResult();
    }

    // Find all supported countries
    public Uni<List<Country>> findSupportedCountries() {
        return find("isSupported", true).list();
    }

    // Find all countries
    public Uni<List<Country>> findAllCountries() {
        return listAll();
    }

    // Update a Country
    public Uni<Country> updateCountry(Country country) {
        return persist(country);
    }

    // Delete a Country by ID
    public Uni<Boolean> deleteCountryById(Long id) {
        return deleteById(id);
    }
}