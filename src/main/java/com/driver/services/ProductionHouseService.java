package com.driver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHouseService {
    private static final Logger logger = LoggerFactory.getLogger(ProductionHouseService.class);

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto) {
        logger.info("Received request to add ProductionHouse: {}", productionHouseEntryDto);
        // Create entity from DTO
        // Set the default ratings to 0.0
        // Check if the production house already exists
        // If it does, throw an exception
        // If it doesn't, save the new production house to the database
        // and return the auto-generated ID
        System.out.println(productionHouseEntryDto);
        ProductionHouse productionHouse = new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setRatings(0.0);

        // Save to DB
        ProductionHouse savedProductionHouse = productionHouseRepository.save(productionHouse);
        System.out.println("Production House saved with ID: " + savedProductionHouse.getId());
        // Return the auto-generated ID
        return savedProductionHouse.getId();
    }




}
