package de.lukasringel.economy.http.server.provider;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.provider.EconomyProvider;
import de.lukasringel.economy.http.server.datasource.implementation.EconomyDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RestServer implementation of the EconomyProvider
 */

@Component
@RequiredArgsConstructor
public class EconomyDataProvider implements EconomyProvider, DataProvider {

    /**
     * Caches our economies locally, so we don't have to query our database every time
     */
    private final List<Economy> localEconomies = new ArrayList<>();

    private final EconomyDataSource economyDataSource;

    /**
     * This method gets called:
     *   1. if the 'refresh' console command was executed
     *   2. if the application bootstraps
     *   3. every 5 minutes
     */
    @Override
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void refresh() {
        localEconomies.clear();
        localEconomies.addAll(economyDataSource.getAllEconomies());
    }

    /**
     * This method creates a new economy
     *
     * @param name       - the unique name of the economy
     * @param startValue - the default worth of every new account
     * @return           - an instance of Economy if the creation was successful
     * @throws EconomyEntityAlreadyExistsException - if the provided name is already used
     */
    @Override
    public Economy createNewEconomy(String name, double startValue) throws EconomyEntityAlreadyExistsException {

        if (localEconomies.stream().anyMatch(economy -> economy.name().equalsIgnoreCase(name))) {
            throw new EconomyEntityAlreadyExistsException("There is already an economy with the name " + name);
        }

        return economyDataSource.createEconomy(name, startValue);
    }

    /**
     * This method searches for an economy with the provided id
     *
     * @param id - the id of the target economy
     * @return   - an optional of the economy since we don't know if an economy with this id exists
     */
    @Override
    public Optional<Economy> getEconomyById(int id) {
        return localEconomies.stream().filter(economy -> economy.id() == id).findFirst();
    }

    /**
     * This method searches for an economy with the provided name
     *
     * @param name - the name of the target economy
     * @return     - an optional of the economy since we don't know if an economy with this name exists
     */
    @Override
    public Optional<Economy> getEconomyByName(String name) {
        return localEconomies.stream().filter(economy -> economy.name().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * This method searches for economies with a modified increaseMultiplier (not 1.0)
     *
     * @return - list with found economies
     */
    @Override
    public List<Economy> getEconomiesWithIncreaseMultiplier() {
        return localEconomies.stream().filter(economy -> economy.increaseMultiplier() != 1.0).collect(Collectors.toList());
    }

    /**
     * This method searches for economies with a modified decreaseMultiplier (not 1.0)
     *
     * @return - list with found economies
     */
    @Override
    public List<Economy> getEconomiesWithDecreaseMultiplier() {
        return localEconomies.stream().filter(economy -> economy.decreaseMultiplier() != 1.0).collect(Collectors.toList());
    }

}
