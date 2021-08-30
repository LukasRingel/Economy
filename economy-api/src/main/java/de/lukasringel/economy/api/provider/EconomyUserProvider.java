package de.lukasringel.economy.api.provider;

import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

/**
 * This interface provides methods to work with users or just query them
 */

public interface EconomyUserProvider {

    /**
     * This method creates a new user
     *
     * @param externalIdentifiers - related external identifiers (Format: key, value, key, value...)
     * @return                    - the new created account
     *
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a user with this identifiers
     */
    EconomyUser createUser(String... externalIdentifiers) throws EconomyEntityAlreadyExistsException;

    /**
     * This method searches for a user with the provided id
     *
     * @param id - the id of the user
     * @return   - an optional of the user since we don't know if a user with this id exists
     */
    Optional<EconomyUser> getUserById(int id);

    /**
     * This method searches for a user with the provided identifier
     *
     * @param key   - the key of the identifier
     * @param query - the query of the identifier
     * @return      - an optional of the user since we don't know if there will be a match
     */
    Optional<EconomyUser> getUserByIdentifier(String key, String query);

    /**
     * This method searches for all suspended users
     *
     * @return - a list of all suspended users
     */
    @HighLoad
    List<EconomyUser> getSuspendedUsers();

    /**
     * This method searches for all users which were created before the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    List<EconomyUser> getUsersCreatedBeforeTimeStamp(long timestamp);

    /**
     * This method searches for all users which were created after the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    List<EconomyUser> getUsersCreatedAfterTimeStamp(long timestamp);

}
