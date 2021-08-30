package de.lukasringel.economy.http.server.controller.rest;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our user rest controller
 */

@RestController
public interface EconomyUserRestController {

    /**
     * This post call creates a new user
     *
     * @param externalIdentifiers - related external identifiers (Format: key, value, key, value...)
     * @return                    - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a user with this identifiers
     */
    @PostMapping("user/create")
    ResponseEntity<EconomyUser> createUser(@Query("externalIdentifiers") String... externalIdentifiers) throws EconomyEntityAlreadyExistsException;

    /**
     * This post call searches for a user with the provided id
     *
     * @param id - the id of the user
     * @return   - an optional of the user since we don't know if a user with this id exists
     */
    @GetMapping("user/by/id")
    ResponseEntity<EconomyUser> getUserById(@Query("id") int id);

    /**
     * This get call searches for a user with the provided identifier
     *
     * @param key   - the key of the identifier
     * @param query - the query of the identifier
     * @return      - an optional of the user since we don't know if there will be a match
     */
    @GetMapping("user/by/identifier")
    ResponseEntity<EconomyUser> getUserByIdentifier(@Query("key") String key,
                                                    @Query("query") String query);

    /**
     * This get call searches for all suspended users
     *
     * @return - a list of all suspended users
     */
    @HighLoad
    @GetMapping("user/suspended")
    ResponseEntity<List<EconomyUser>> getSuspendedUsers();

    /**
     * This get call searches for all users which were created before the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    @GetMapping("user/created/before")
    ResponseEntity<List<EconomyUser>> getUsersCreatedBeforeTimeStamp(@Query("timestamp") long timestamp);

    /**
     * This get call searches for all users which were created after the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    @GetMapping("user/created/after")
    ResponseEntity<List<EconomyUser>> getUsersCreatedAfterTimeStamp(@Query("timestamp") long timestamp);

}
