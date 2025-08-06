package com.mhm.services;

import com.mhm.entities.Country;
import com.mhm.entities.ServiceCategory;
import com.mhm.repositories.CountryRepository;
import com.mhm.repositories.ServiceCategoryRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class DriverService {

    @Inject
    CountryRepository countryRepository;

    @Inject
    ServiceCategoryRepository serviceCategoryRepository;

    /**
     * Get complete driver service configuration for a country
     */
    public Uni<Country> getDriverServiceConfig(String countryCode) {
        return countryRepository.findCountryByCode(countryCode)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found: " + countryCode))
                .onItem().transform(country -> {
                    if (!country.getIsSupported()) {
                        throw new RuntimeException("Driver services not supported in " + country.getName());
                    }
                    return country;
                });
    }

    /**
     * Get all supported countries with their service configurations
     */
    public Uni<List<Country>> getSupportedCountriesConfig() {
        return countryRepository.findSupportedCountries();
    }

    /**
     * Check if driver services are available in a specific country
     */
    public Uni<Boolean> isServiceAvailable(String countryCode) {
        return countryRepository.findCountryByCode(countryCode)
                .onItem().transform(country -> country != null && country.getIsSupported())
                .onItem().ifNull().continueWith(false);
    }

    /**
     * Get active service categories for a country
     */
    public Uni<List<ServiceCategory>> getActiveServiceCategories(String countryCode) {
        return countryRepository.findCountryByCode(countryCode)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found: " + countryCode))
                .onItem().transformToUni(country -> 
                    serviceCategoryRepository.findActiveServiceCategoriesByCountry(country.id));
    }

    /**
     * Get country information by code
     */
    public Uni<Country> getCountryByCode(String countryCode) {
        return countryRepository.findCountryByCode(countryCode)
                .onItem().ifNull().failWith(() -> new RuntimeException("Country not found: " + countryCode));
    }

    /**
     * Get all countries (for admin purposes)
     */
    public Uni<List<Country>> getAllCountries() {
        return countryRepository.findAllCountries();
    }
}