package de.lukasringel.economy.http.server.controller.implementation;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.provider.EconomyUserProvider;
import de.lukasringel.economy.http.server.controller.RestController;
import de.lukasringel.economy.http.server.controller.rest.EconomyUserRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class implements our EconomyUserRestController
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class EconomyUserRestControllerImplementation implements RestController<EconomyUser>, EconomyUserRestController {

    private final EconomyUserProvider userProvider;

    /**
     * This post call creates a new user
     *
     * @param externalIdentifiers - related external identifiers (Format: key, value, key, value...)
     * @return                    - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a user with this identifiers
     */
    @Override
    public ResponseEntity<EconomyUser> createUser(String... externalIdentifiers) throws EconomyEntityAlreadyExistsException {
        try {
            EconomyUser user = userProvider.createUser(externalIdentifiers);
            log.info("Successfully created a new user with identifiers: " + String.join(", ", externalIdentifiers));
            return ResponseEntity.ok(user);
        } catch (EconomyEntityAlreadyExistsException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This post call searches for a user with the provided id
     *
     * @param id - the id of the user
     * @return   - an optional of the user since we don't know if a user with this id exists
     */
    @Override
    public ResponseEntity<EconomyUser> getUserById(int id) {
        return userProvider.getUserById(id).map(ResponseEntity::ok).orElseGet(this::errorResponseEntity);
    }

    /**
     * This get call searches for a user with the provided identifier
     *
     * @param key   - the key of the identifier
     * @param query - the query of the identifier
     * @return      - an optional of the user since we don't know if there will be a match
     */
    @Override
    public ResponseEntity<EconomyUser> getUserByIdentifier(String key, String query) {
        return userProvider.getUserByIdentifier(key, query).map(ResponseEntity::ok).orElseGet(this::errorResponseEntity);
    }

    /**
     * This get call searches for all suspended users
     *
     * @return - a list of all suspended users
     */
    @Override
    public ResponseEntity<List<EconomyUser>> getSuspendedUsers() {
        return ResponseEntity.ok(userProvider.getSuspendedUsers());
    }

    /**
     * This get call searches for all users which were created before the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @Override
    public ResponseEntity<List<EconomyUser>> getUsersCreatedBeforeTimeStamp(long timestamp) {
        return ResponseEntity.ok(userProvider.getUsersCreatedBeforeTimeStamp(timestamp));
    }

    /**
     * This get call searches for all users which were created after the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @Override
    public ResponseEntity<List<EconomyUser>> getUsersCreatedAfterTimeStamp(long timestamp) {
        return ResponseEntity.ok(userProvider.getUsersCreatedAfterTimeStamp(timestamp));
    }
}