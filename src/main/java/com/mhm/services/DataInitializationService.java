//package com.mhm.services;
//
//import com.mhm.entities.Country;
//import com.mhm.entities.EmergencyContact;
//import com.mhm.entities.ServiceCategory;
//import com.mhm.entities.ServiceSubCategory;
//import com.mhm.repositories.CountryRepository;
//import com.mhm.repositories.EmergencyContactRepository;
//import com.mhm.repositories.ServiceCategoryRepository;
//import com.mhm.repositories.ServiceSubCategoryRepository;
//import io.quarkus.runtime.Startup;
//import io.smallrye.mutiny.Uni;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.List;
//
//@ApplicationScoped
//@Startup
//public class DataInitializationService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializationService.class);
//
//    @Inject
//    CountryRepository countryRepository;
//
//    @Inject
//    EmergencyContactRepository emergencyContactRepository;
//
//    @Inject
//    ServiceCategoryRepository serviceCategoryRepository;
//
//    @Inject
//    ServiceSubCategoryRepository serviceSubCategoryRepository;
//
//    @PostConstruct
//    public void initialize() {
//        LOGGER.info("Starting reactive data initialization for driver service...");
//
//        // Reactive initialization - fire and forget pattern
//        initializeData()
//            .subscribe().with(
//                result -> LOGGER.info("Reactive data initialization completed successfully"),
//                failure -> LOGGER.error("Reactive data initialization failed: " + failure.getMessage(), failure)
//            );
//    }
//
//    /**
//     * Public reactive method for initializing data.
//     * Can be called from other services or endpoints if needed.
//     */
//    public Uni<Void> initializeData() {
//        return initializeBosniaAndHerzegovina();
//    }
//
//    /**
//     * Reactive method to check if data is already initialized.
//     * Returns true if Bosnia and Herzegovina country exists.
//     */
//    public Uni<Boolean> isDataInitialized() {
//        return countryRepository.findCountryByCode("BA")
//                .onItem().transform(country -> country != null);
//    }
//
//    /**
//     * Reactive method to force re-initialization of data.
//     * This will recreate all data even if it already exists.
//     */
//    public Uni<Void> reinitializeData() {
//        LOGGER.info("Force reinitializing data...");
//        return countryRepository.findCountryByCode("BA")
//                .onItem().transformToUni(existingCountry -> {
//                    if (existingCountry != null) {
//                        LOGGER.info("Deleting existing Bosnia and Herzegovina data before reinitializing");
//                        return countryRepository.deleteCountryById(existingCountry.id)
//                                .onItem().transformToUni(ignored -> initializeBosniaAndHerzegovina());
//                    }
//                    return initializeBosniaAndHerzegovina();
//                });
//    }
//
//    private Uni<Void> initializeBosniaAndHerzegovina() {
//        // Create Bosnia and Herzegovina country
//        Country bosnia = new Country();
//        bosnia.setCode("BA");
//        bosnia.setName("Bosnia and Herzegovina");
//        bosnia.setFlag("🇧🇦");
//        bosnia.setCurrency("BAM");
//        bosnia.setTimezone("Europe/Sarajevo");
//        bosnia.setIsSupported(true);
//
//        return countryRepository.findCountryByCode("BA")
//                .onItem().transformToUni(existingCountry -> {
//                    if (existingCountry != null) {
//                        // Country already exists, skip initialization
//                        LOGGER.info("Bosnia and Herzegovina already exists, skipping initialization");
//                        return Uni.createFrom().voidItem();
//                    }
//
//                    LOGGER.info("Creating Bosnia and Herzegovina with complete driver service configuration");
//                    return countryRepository.createCountry(bosnia)
//                            .onItem().transformToUni(savedCountry ->
//                                createEmergencyContacts(savedCountry)
//                                        .onItem().transformToUni(ignored -> createServiceCategories(savedCountry))
//                            );
//                });
//    }
//
//    private Uni<Void> createEmergencyContacts(Country country) {
//        List<EmergencyContact> emergencyContacts = Arrays.asList(
//                createEmergencyContact("general", "112", "General emergency number for police, fire, and medical", country),
//                createEmergencyContact("police", "122", "Police emergency", country),
//                createEmergencyContact("medical", "124", "Medical emergency", country),
//                createEmergencyContact("fire", "123", "Fire department", country)
//        );
//
//        return Uni.combine().all().unis(
//                emergencyContacts.stream()
//                        .map(emergencyContactRepository::createEmergencyContact)
//                        .toArray(Uni[]::new)
//        ).discardItems();
//    }
//
//    private EmergencyContact createEmergencyContact(String type, String number, String description, Country country) {
//        EmergencyContact contact = new EmergencyContact();
//        contact.setType(type);
//        contact.setNumber(number);
//        contact.setDescription(description);
//        contact.setCountry(country);
//        return contact;
//    }
//
//    private Uni<Void> createServiceCategories(Country country) {
//        // Create service categories with their subcategories based on your JSON
//        return createTowTruckService(country)
//                .onItem().transformToUni(ignored -> createCarBatteryService(country))
//                .onItem().transformToUni(ignored -> createTireService(country))
//                .onItem().transformToUni(ignored -> createFuelService(country))
//                .onItem().transformToUni(ignored -> createLockoutService(country))
//                .onItem().transformToUni(ignored -> createEVSupportService(country))
//                .onItem().transformToUni(ignored -> createDiagnosticService(country))
//                .onItem().transformToUni(ignored -> createVehicleInspectionService(country))
//                .onItem().transformToUni(ignored -> createVehicleTransportService(country));
//    }
//
//    private Uni<Void> createTowTruckService(Country country) {
//        ServiceCategory towTruck = createServiceCategory(1, "Tow Truck", "category_tow_truck",
//                "Towing service for your vehicle", true, country);
//
//        return serviceCategoryRepository.createServiceCategory(towTruck)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(101, "Standard Towing", "tow_standard", "Typical towing service for most vehicles.", true, savedCategory),
//                            createSubCategory(102, "Flatbed Towing", "tow_flatbed", "Flatbed transport for vehicles that require careful handling.", true, savedCategory),
//                            createSubCategory(103, "Heavy-Duty Towing", "tow_heavy_duty", "Specialized towing for large or heavy vehicles.", true, savedCategory),
//                            createSubCategory(104, "Winch Recovery", "tow_winch_recovery", "Use of a winch to recover vehicles stuck in difficult situations.", true, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createCarBatteryService(Country country) {
//        ServiceCategory carBattery = createServiceCategory(2, "Car Battery", "category_car_battery",
//                "Jumpstart or replace your car battery", true, country);
//
//        return serviceCategoryRepository.createServiceCategory(carBattery)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(201, "Jump Start", "battery_jump_start", "Emergency jump start service.", true, savedCategory),
//                            createSubCategory(202, "Battery Replacement", "battery_replacement", "Replacement service for a failing battery.", true, savedCategory),
//                            createSubCategory(203, "Battery Testing", "battery_testing", "Diagnostic testing to check battery health.", true, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createTireService(Country country) {
//        ServiceCategory tireService = createServiceCategory(3, "Tire Service", "category_flat_tire",
//                "Help with tire changes or puncture repairs", true, country);
//
//        return serviceCategoryRepository.createServiceCategory(tireService)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(301, "Tire Change", "tire_change", "On-site tire replacement using your spare.", true, savedCategory),
//                            createSubCategory(302, "Puncture Repair", "tire_puncture_repair", "Repairing punctures to save your tire.", true, savedCategory),
//                            createSubCategory(303, "Spare Tire Provision", "spare_tire_provision", "Service provider supplies a new spare tire for the user.", true, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createFuelService(Country country) {
//        ServiceCategory fuelService = createServiceCategory(4, "Fuel Service", "category_fuel_delivery",
//                "Get emergency fuel delivered to your location", true, country);
//
//        return serviceCategoryRepository.createServiceCategory(fuelService)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(401, "Regular Fuel Delivery", "fuel_regular", "Delivery of regular gasoline to get you moving.", true, savedCategory),
//                            createSubCategory(402, "Premium Fuel Delivery", "fuel_premium", "Delivery of premium gasoline for high-performance vehicles.", true, savedCategory),
//                            createSubCategory(403, "Incorrect Fuel Pump-Out", "fuel_incorrect_pump_out", "Draining and replacing fuel when the wrong type was added.", true, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createLockoutService(Country country) {
//        ServiceCategory lockoutService = createServiceCategory(5, "Lockout Service", "category_lockout",
//                "Locked out of your car? Get assistance", true, country);
//
//        return serviceCategoryRepository.createServiceCategory(lockoutService)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(501, "Emergency Unlocking", "lockout_emergency_unlock", "Rapid response to get you back in your car.", true, savedCategory),
//                            createSubCategory(502, "Key Replacement", "lockout_key_replacement", "Replacement service for lost or locked keys.", true, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createEVSupportService(Country country) {
//        ServiceCategory evSupport = createServiceCategory(6, "EV Support", "category_ev_support",
//                "Dedicated roadside services for electric vehicles (EVs)", false, country);
//
//        return serviceCategoryRepository.createServiceCategory(evSupport)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(601, "Mobile Charging Unit", "ev_mobile_charging", "On-demand battery charging for stranded EVs.", false, savedCategory),
//                            createSubCategory(602, "Battery Health Check", "ev_battery_check", "Diagnostics to assess the condition of your EV battery.", false, savedCategory),
//                            createSubCategory(603, "EV-Compatible Towing", "ev_towing", "Towing service optimized for electric vehicles.", false, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createDiagnosticService(Country country) {
//        ServiceCategory diagnostic = createServiceCategory(7, "Diagnostic", "category_diagnostic",
//                "Get a diagnostic check for your vehicle", false, country);
//
//        return serviceCategoryRepository.createServiceCategory(diagnostic)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(701, "Engine Diagnostics", "diagnostic_engine", "Comprehensive engine performance checks.", false, savedCategory),
//                            createSubCategory(702, "Electrical Systems Check", "diagnostic_electrical", "Inspection of the vehicle's electrical components.", false, savedCategory),
//                            createSubCategory(703, "Transmission Check", "diagnostic_transmission", "Evaluation of transmission health and performance.", false, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createVehicleInspectionService(Country country) {
//        ServiceCategory vehicleInspection = createServiceCategory(8, "Vehicle Inspection", "category_vehicle_inspection",
//                "Ensure your vehicle is roadworthy and meets safety standards before registration.", false, country);
//
//        return serviceCategoryRepository.createServiceCategory(vehicleInspection)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(801, "Safety Inspection", "inspection_safety", "Ensuring the vehicle meets all safety and roadworthiness standards.", false, savedCategory),
//                            createSubCategory(802, "Emissions Test", "inspection_emissions", "Check for compliance with emissions regulations.", false, savedCategory),
//                            createSubCategory(803, "Pre-Purchase Inspection", "inspection_pre_purchase", "A thorough inspection for used car buyers.", false, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private Uni<Void> createVehicleTransportService(Country country) {
//        ServiceCategory vehicleTransport = createServiceCategory(9, "Vehicle Transport", "category_vehicle_transport",
//                "Transport your vehicle from one location to another, safely and reliably.", false, country);
//
//        return serviceCategoryRepository.createServiceCategory(vehicleTransport)
//                .onItem().transformToUni(savedCategory -> {
//                    List<ServiceSubCategory> subCategories = Arrays.asList(
//                            createSubCategory(901, "Local Transport", "transport_local", "Vehicle transport within a short distance or city.", false, savedCategory),
//                            createSubCategory(902, "International Transport", "transport_international", "Cross-border or overseas vehicle transport service.", false, savedCategory),
//                            createSubCategory(903, "Enclosed Transport", "transport_enclosed", "Premium enclosed transport for luxury or exotic vehicles.", false, savedCategory)
//                    );
//
//                    return Uni.combine().all().unis(
//                            subCategories.stream()
//                                    .map(serviceSubCategoryRepository::createServiceSubCategory)
//                                    .toArray(Uni[]::new)
//                    ).discardItems();
//                });
//    }
//
//    private ServiceCategory createServiceCategory(Integer externalId, String name, String identifier, String description, Boolean active, Country country) {
//        ServiceCategory category = new ServiceCategory();
//        category.setExternalId(externalId);
//        category.setName(name);
//        category.setIdentifier(identifier);
//        category.setDescription(description);
//        category.setActive(active);
//        category.setCountry(country);
//        return category;
//    }
//
//    private ServiceSubCategory createSubCategory(Integer externalId, String name, String identifier, String description, Boolean active, ServiceCategory serviceCategory) {
//        ServiceSubCategory subCategory = new ServiceSubCategory();
//        subCategory.setExternalId(externalId);
//        subCategory.setName(name);
//        subCategory.setIdentifier(identifier);
//        subCategory.setDescription(description);
//        subCategory.setActive(active);
//        subCategory.setServiceCategory(serviceCategory);
//        return subCategory;
//    }
//}