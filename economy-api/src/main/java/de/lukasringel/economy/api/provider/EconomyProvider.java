package de.lukasringel.economy.api.provider;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.Economy;

import java.util.List;
import java.util.Optional;

/**
 * This interface provides methods to work with economies or just query them
 */

public interface EconomyProvider {

    /**
     * This method creates a new economy
     *
     * @param name       - the unique name of the economy
     * @param startValue - the default worth of every new account
     * @return           - an instance of Economy if the creation was successful
     *
     * @throws EconomyEntityAlreadyExistsException - if the provided name is already used
     */
    Economy createNewEconomy(String name, double startValue) throws EconomyEntityAlreadyExistsException;

    /**
     * This method searches for an economy with the provided id
     *
     * @param id - the id of the target economy
     * @return   - an optional of the economy since we don't know if an economy with this id exists
     */
    Optional<Economy> getEconomyById(int id);

    /**
     * This method searches for an economy with the provided name
     *
     * @param name - the name of the target economy
     * @return     - an optional of the economy since we don't know if an economy with this name exists
     */
    Optional<Economy> getEconomyByName(String name);

    /**
     * This method searches for economies with a modified increaseMultiplier (not 1.0)
     *
     * @return - list with found economies
     */
    List<Economy> getEconomiesWithIncreaseMultiplier();

    /**
     * This method searches for economies with a modified decreaseMultiplier (not 1.0)
     *
     * @return - list with found economies
     */
    List<Economy> getEconomiesWithDecreaseMultiplier();

}
