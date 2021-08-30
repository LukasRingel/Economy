package de.lukasringel.economy.http.server.controller.implementation;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.http.server.controller.RestController;
import de.lukasringel.economy.http.server.controller.rest.EconomyRestController;
import de.lukasringel.economy.http.server.provider.EconomyDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class implements our EconomyRestController
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class EconomyRestControllerImplementation implements RestController<Economy>, EconomyRestController {

    private final EconomyDataProvider economyDataProvider;

    /**
     * This post call creates a new economy in our database
     *
     * @param name       - the name the new economy
     * @param startValue - the start value of the new economy
     * @return           - our ResponseEntity
     */
    @Override
    public ResponseEntity<Economy> createEconomy(String name, double startValue) {
        try {
            Economy economy = economyDataProvider.createNewEconomy(name, startValue);
            log.info("Successfully created a new economy with name " + name + " and start-value " + startValue + ".");
            return ResponseEntity.ok(economy);
        } catch (EconomyEntityAlreadyExistsException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This get call searches for an economy with the provided id
     *
     * @param id - the id we search for
     * @return   - our ResponseEntity
     */
    @Override
    public ResponseEntity<Economy> getEconomyById(int id) {
        return economyDataProvider.getEconomyById(id).map(ResponseEntity::ok).orElseGet(this::errorResponseEntity);
    }

    /**
     * This get call searches for an economy with the provided name
     *
     * @param name - the name we search for
     * @return     - our ResponseEntity
     */
    @Override
    public ResponseEntity<Economy> getEconomyByName(String name) {
        return economyDataProvider.getEconomyByName(name).map(ResponseEntity::ok).orElseGet(this::errorResponseEntity);
    }

    /**
     * This get call searches for economies with an increase multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @Override
    public ResponseEntity<List<Economy>> getEconomiesWithIncreaseMultiplier() {
        return ResponseEntity.ok(economyDataProvider.getEconomiesWithIncreaseMultiplier());
    }

    /**
     * This get call searches for economies with a decrease multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @Override
    public ResponseEntity<List<Economy>> getEconomiesWithDecreaseMultiplier() {
        return ResponseEntity.ok(economyDataProvider.getEconomiesWithDecreaseMultiplier());
    }

}
