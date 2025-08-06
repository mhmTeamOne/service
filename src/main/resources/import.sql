-- ===============================================
-- MOBA Driver Service Data Initialization
-- ===============================================
-- This file is automatically executed by Quarkus on startup
-- when quarkus.hibernate-orm.database.generation=drop-and-create

-- ===============================================
-- 1. INSERT COUNTRIES
-- ===============================================
INSERT INTO countries (id, code, name, flag, currency, timezone, is_supported) 
VALUES (1, 'BA', 'Bosnia and Herzegovina', '🇧🇦', 'BAM', 'Europe/Sarajevo', true);

-- ===============================================
-- 2. INSERT EMERGENCY CONTACTS
-- ===============================================
INSERT INTO emergency_contacts (id, type, number, description, country_id) VALUES
(1, 'general', '112', 'General emergency number for police, fire, and medical', 1),
(2, 'police', '122', 'Police emergency', 1),
(3, 'medical', '124', 'Medical emergency', 1),
(4, 'fire', '123', 'Fire department', 1);

-- ===============================================
-- 3. INSERT SERVICE CATEGORIES
-- ===============================================

-- Tow Truck Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (1, 1, 'Tow Truck', 'category_tow_truck', 'Towing service for your vehicle', true, 1);

-- Car Battery Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (2, 2, 'Car Battery', 'category_car_battery', 'Jumpstart or replace your car battery', true, 1);

-- Tire Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (3, 3, 'Tire Service', 'category_flat_tire', 'Help with tire changes or puncture repairs', true, 1);

-- Fuel Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (4, 4, 'Fuel Service', 'category_fuel_delivery', 'Get emergency fuel delivered to your location', true, 1);

-- Lockout Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (5, 5, 'Lockout Service', 'category_lockout', 'Locked out of your car? Get assistance', true, 1);

-- EV Support Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (6, 6, 'EV Support', 'category_ev_support', 'Dedicated roadside services for electric vehicles (EVs)', false, 1);

-- Diagnostic Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (7, 7, 'Diagnostic', 'category_diagnostic', 'Get a diagnostic check for your vehicle', false, 1);

-- Vehicle Inspection Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (8, 8, 'Vehicle Inspection', 'category_vehicle_inspection', 'Ensure your vehicle is roadworthy and meets safety standards before registration.', false, 1);

-- Vehicle Transport Service
INSERT INTO service_categories (id, external_id, name, identifier, description, active, country_id) 
VALUES (9, 9, 'Vehicle Transport', 'category_vehicle_transport', 'Transport your vehicle from one location to another, safely and reliably.', false, 1);

-- ===============================================
-- 4. INSERT SERVICE SUBCATEGORIES
-- ===============================================

-- Tow Truck Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(101, 101, 'Standard Towing', 'tow_standard', 'Typical towing service for most vehicles.', true, 1),
(102, 102, 'Flatbed Towing', 'tow_flatbed', 'Flatbed transport for vehicles that require careful handling.', true, 1),
(103, 103, 'Heavy-Duty Towing', 'tow_heavy_duty', 'Specialized towing for large or heavy vehicles.', true, 1),
(104, 104, 'Winch Recovery', 'tow_winch_recovery', 'Use of a winch to recover vehicles stuck in difficult situations.', true, 1);

-- Car Battery Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(201, 201, 'Jump Start', 'battery_jump_start', 'Emergency jump start service.', true, 2),
(202, 202, 'Battery Replacement', 'battery_replacement', 'Replacement service for a failing battery.', true, 2),
(203, 203, 'Battery Testing', 'battery_testing', 'Diagnostic testing to check battery health.', true, 2);

-- Tire Service Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(301, 301, 'Tire Change', 'tire_change', 'On-site tire replacement using your spare.', true, 3),
(302, 302, 'Puncture Repair', 'tire_puncture_repair', 'Repairing punctures to save your tire.', true, 3),
(303, 303, 'Spare Tire Provision', 'spare_tire_provision', 'Service provider supplies a new spare tire for the user.', true, 3);

-- Fuel Service Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(401, 401, 'Regular Fuel Delivery', 'fuel_regular', 'Delivery of regular gasoline to get you moving.', true, 4),
(402, 402, 'Premium Fuel Delivery', 'fuel_premium', 'Delivery of premium gasoline for high-performance vehicles.', true, 4),
(403, 403, 'Incorrect Fuel Pump-Out', 'fuel_incorrect_pump_out', 'Draining and replacing fuel when the wrong type was added.', true, 4);

-- Lockout Service Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(501, 501, 'Emergency Unlocking', 'lockout_emergency_unlock', 'Rapid response to get you back in your car.', true, 5),
(502, 502, 'Key Replacement', 'lockout_key_replacement', 'Replacement service for lost or locked keys.', true, 5);

-- EV Support Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(601, 601, 'Mobile Charging Unit', 'ev_mobile_charging', 'On-demand battery charging for stranded EVs.', false, 6),
(602, 602, 'Battery Health Check', 'ev_battery_check', 'Diagnostics to assess the condition of your EV battery.', false, 6),
(603, 603, 'EV-Compatible Towing', 'ev_towing', 'Towing service optimized for electric vehicles.', false, 6);

-- Diagnostic Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(701, 701, 'Engine Diagnostics', 'diagnostic_engine', 'Comprehensive engine performance checks.', false, 7),
(702, 702, 'Electrical Systems Check', 'diagnostic_electrical', 'Inspection of the vehicle''s electrical components.', false, 7),
(703, 703, 'Transmission Check', 'diagnostic_transmission', 'Evaluation of transmission health and performance.', false, 7);

-- Vehicle Inspection Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(801, 801, 'Safety Inspection', 'inspection_safety', 'Ensuring the vehicle meets all safety and roadworthiness standards.', false, 8),
(802, 802, 'Emissions Test', 'inspection_emissions', 'Check for compliance with emissions regulations.', false, 8),
(803, 803, 'Pre-Purchase Inspection', 'inspection_pre_purchase', 'A thorough inspection for used car buyers.', false, 8);

-- Vehicle Transport Subcategories
INSERT INTO service_subcategories (id, external_id, name, identifier, description, active, service_category_id) VALUES
(901, 901, 'Local Transport', 'transport_local', 'Vehicle transport within a short distance or city.', false, 9),
(902, 902, 'International Transport', 'transport_international', 'Cross-border or overseas vehicle transport service.', false, 9),
(903, 903, 'Enclosed Transport', 'transport_enclosed', 'Premium enclosed transport for luxury or exotic vehicles.', false, 9);

-- ===============================================
-- Update sequences for PostgreSQL (if needed)
-- ===============================================
-- Note: These are only needed if you're using PostgreSQL and want to ensure
-- sequence values are set correctly for future inserts

SELECT setval('countries_id_seq', (SELECT MAX(id) FROM countries));
SELECT setval('emergency_contacts_id_seq', (SELECT MAX(id) FROM emergency_contacts));
SELECT setval('service_categories_id_seq', (SELECT MAX(id) FROM service_categories));
SELECT setval('service_subcategories_id_seq', (SELECT MAX(id) FROM service_subcategories));
