package com.mhm.resources;

import com.mhm.entities.Country;
import com.mhm.entities.EmergencyContact;
import com.mhm.services.CountryService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountryResource {

    @Inject
    CountryService countryService;

    @GET
    public Uni<List<Country>> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GET
    @Path("/supported")
    public Uni<List<Country>> getSupportedCountries() {
        return countryService.getSupportedCountries();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getCountryById(@PathParam("id") Long id) {
        return countryService.getCountryById(id)
                .onItem().transform(country -> Response.ok(country).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    @GET
    @Path("/code/{code}")
    public Uni<Response> getCountryByCode(@PathParam("code") String code) {
        return countryService.getCountryByCode(code)
                .onItem().transform(country -> Response.ok(country).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    @POST
    public Uni<Response> createCountry(Country country) {
        return countryService.createCountry(country)
                .onItem().transform(savedCountry -> 
                    Response.status(Response.Status.CREATED).entity(savedCountry).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateCountry(@PathParam("id") Long id, Country country) {
        return countryService.updateCountry(id, country)
                .onItem().transform(updatedCountry -> Response.ok(updatedCountry).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteCountry(@PathParam("id") Long id) {
        return countryService.deleteCountry(id)
                .onItem().transform(deleted -> Response.noContent().build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }

    @GET
    @Path("/{id}/emergency-contacts")
    public Uni<List<EmergencyContact>> getEmergencyContacts(@PathParam("id") Long countryId) {
        return countryService.getEmergencyContactsByCountry(countryId);
    }

    @GET
    @Path("/{id}/emergency-contacts/{type}")
    public Uni<Response> getEmergencyContactByType(@PathParam("id") Long countryId, @PathParam("type") String type) {
        return countryService.getEmergencyContactByCountryAndType(countryId, type)
                .onItem().transform(contact -> Response.ok(contact).build())
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", throwable.getMessage()))
                            .build());
    }
}