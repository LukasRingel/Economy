package de.lukasringel.economy.http.server.controller.rest;

import de.lukasringel.economy.api.model.economy.Economy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our economy rest controller
 */

@RestController
public interface EconomyRestController {

    /**
     * This post call creates a new economy in our database
     *
     * @param name       - the name the new economy
     * @param startValue - the start value of the new economy
     * @return           - our ResponseEntity
     */
    @PostMapping("economy/create")
    ResponseEntity<Economy> createEconomy(@Query("name") String name,
                                          @Query("startValue") double startValue);

    /**
     * This get call searches for an economy with the provided id
     *
     * @param id       - the id we search for
     * @return         - our ResponseEntity
     */
    @GetMapping("economy/by/id")
    ResponseEntity<Economy> getEconomyById(@Query("id") int id);

    /**
     * This get call searches for an economy with the provided name
     *
     * @param name       - the name we search for
     * @return           - our ResponseEntity
     */
    @GetMapping("economy/by/name")
    ResponseEntity<Economy> getEconomyByName(@Query("name") String name);

    /**
     * This get call searches for economies with an increase multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @GetMapping("economy/multiplier/increase")
    ResponseEntity<List<Economy>> getEconomiesWithIncreaseMultiplier();

    /**
     * This get call searches for economies with a decrease multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @GetMapping("economy/multiplier/increase")
    ResponseEntity<List<Economy>> getEconomiesWithDecreaseMultiplier();

}
