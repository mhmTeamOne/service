package com.mhm.resources;

import com.mhm.entities.Country;
import com.mhm.services.DriverService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/driver-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriverServiceResource {

    @Inject
    DriverService driverService;

    /**
     * Get complete driver service configuration for a country
     * This endpoint returns the full structure matching your JSON format
     */
    @GET
    @Path("/config/country/{countryCode}")
    public Uni<Response> getDriverServiceConfig(@PathParam("countryCode") String countryCode) {
        return driverService.getDriverServiceConfig(countryCode)
                .onItem().transform(country -> Response.ok(country).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    /**
     * Get all supported countries with their complete service configurations
     */
    @GET
    @Path("/config/supported-countries")
    public Uni<List<Country>> getSupportedCountriesConfig() {
        return driverService.getSupportedCountriesConfig();
    }

    /**
     * Check service availability for a specific country
     */
    @GET
    @Path("/availability/country/{countryCode}")
    public Uni<Response> getServiceAvailability(@PathParam("countryCode") String countryCode) {
        return driverService.getCountryByCode(countryCode)
                .onItem().transform(country -> 
                    Response.ok(Map.of(
                        "country", country.getName(),
                        "code", country.getCode(),
                        "available", country.getIsSupported()
                    )).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", "Country not found"))
                            .build());
    }

    /**
     * Get active service categories for a country
     */
    @GET
    @Path("/services/country/{countryCode}")
    public Uni<Response> getActiveServiceCategories(@PathParam("countryCode") String countryCode) {
        return driverService.getActiveServiceCategories(countryCode)
                .onItem().transform(services -> Response.ok(services).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    /**
     * Health check endpoint for the driver service
     */
    @GET
    @Path("/health")
    public Uni<Response> healthCheck() {
        return Uni.createFrom().item(
                Response.ok(Map.of(
                    "status", "UP",
                    "service", "driver-service",
                    "timestamp", java.time.Instant.now().toString()
                )).build()
        );
    }

    /**
     * Get all countries (admin endpoint)
     */
    @GET
    @Path("/admin/countries")
    public Uni<List<Country>> getAllCountries() {
        return driverService.getAllCountries();
    }
}