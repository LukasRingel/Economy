package de.lukasringel.economy.http.server.controller.implementation;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.exception.EconomyEntityNotFoundException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.provider.EconomyAccountProvider;
import de.lukasringel.economy.http.server.controller.RestController;
import de.lukasringel.economy.http.server.controller.rest.EconomyAccountRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class implements our EconomyAccountRestController
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class EconomyAccountRestControllerImplementation implements RestController<EconomyAccount>, EconomyAccountRestController {

    private final EconomyAccountProvider accountProvider;

    /**
     * This post call creates a new account for an existing user
     *
     * @param userId    - the id of the user
     * @param economyId - the id of the economy
     * @return          - the new created account
     */
    @Override
    public ResponseEntity<EconomyAccount> createAccount(int userId, int economyId) {
        try {
            EconomyAccount account = accountProvider.createAccount(userId, economyId);
            log.info("Successfully created a new account for userId " + userId + " and economyId " + economyId + ".");
            return ResponseEntity.ok(account);
        } catch (EconomyEntityAlreadyExistsException | EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This post call creates a new account for an existing user
     *
     * @param userId  - the id of the user
     * @param economy - the target economy
     * @return        - the new created account
     */
    @Override
    public ResponseEntity<EconomyAccount> createAccount(int userId, Economy economy) {
        try {
            EconomyAccount account = accountProvider.createAccount(userId, economy);
            log.info("Successfully created a new account for userId " + userId + " and economy " + economy.name() + ".");
            return ResponseEntity.ok(account);
        } catch (EconomyEntityAlreadyExistsException | EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This post call creates a new account for an existing user
     *
     * @param user      - the target user
     * @param economyId - the target economyId
     * @return          - the new created account
     */
    @Override
    public ResponseEntity<EconomyAccount> createAccount(EconomyUser user, int economyId) {
        try {
            EconomyAccount account = accountProvider.createAccount(user, economyId);
            log.info("Successfully created a new account for userId " + user.id() + " and economyId " + economyId + ".");
            return ResponseEntity.ok(account);
        } catch (EconomyEntityAlreadyExistsException | EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This post call creates a new account for an existing user
     *
     * @param user    - the target user
     * @param economy - the target economy
     * @return        - the new created account
     */
    @Override
    public ResponseEntity<EconomyAccount> createAccount(EconomyUser user, Economy economy) {
        try {
            EconomyAccount account = accountProvider.createAccount(user, economy);
            log.info("Successfully created a new account for userId " + user.id() + " and economy " + economy.name() + ".");
            return ResponseEntity.ok(account);
        } catch (EconomyEntityAlreadyExistsException | EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return errorResponseEntity();
        }
    }

    /**
     * This post call increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @Override
    public ResponseEntity<EconomyAccount> increaseAccountWorth(EconomyAccount economyAccount, double amount) {
        return ResponseEntity.ok(accountProvider.increaseAccountWorth(economyAccount, amount));
    }

    /**
     * This post call increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @Override
    public ResponseEntity<EconomyAccount> increaseAccountWorth(EconomyAccount economyAccount, double amount, String comment) {
        return ResponseEntity.ok(accountProvider.increaseAccountWorth(economyAccount, amount, comment));
    }

    /**
     * This post call decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @Override
    public ResponseEntity<EconomyAccount> decreaseAccountWorth(EconomyAccount economyAccount, double amount) {
        return ResponseEntity.ok(accountProvider.decreaseAccountWorth(economyAccount, amount));
    }

    /**
     * This post call decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @Override
    public ResponseEntity<EconomyAccount> decreaseAccountWorth(EconomyAccount economyAccount, double amount, String comment) {
        return ResponseEntity.ok(accountProvider.decreaseAccountWorth(economyAccount, amount, comment));
    }

    /**
     * This get call searches for an account with the provided id
     *
     * @param accountId - the id of the account
     * @return          - an optional of the account since we don't know if an account with this id exists
     */
    @Override
    public ResponseEntity<EconomyAccount> getAccountById(int accountId) {
        return accountProvider.getAccountById(accountId).map(ResponseEntity::ok).orElseGet(this::errorResponseEntity);
    }

    /**
     * This get call searches for all accounts of the provided economy by id
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @return        - a list of all found accounts
     */
    @Override
    public ResponseEntity<List<EconomyAccount>> getAccountsOfEconomy(Economy economy) {
        return ResponseEntity.ok(accountProvider.getAccountsOfEconomy(economy));
    }

    /**
     * This get call searches for all accounts of an economy above the provided amount
     * This call can produce high load!
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query above
     * @return          - a list of all found accounts
     */
    @Override
    public ResponseEntity<List<EconomyAccount>> getAccountsAboveAmount(int economyId, double amount) {
        try {
            return ResponseEntity.ok(accountProvider.getAccountsAboveAmount(economyId, amount));
        } catch (EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This get call searches for all accounts of an economy above the provided amount
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query above
     * @return        - a list of all found accounts
     */
    @Override
    public ResponseEntity<List<EconomyAccount>> getAccountsAboveAmount(Economy economy, double amount) {
        return ResponseEntity.ok(accountProvider.getAccountsAboveAmount(economy, amount));
    }

    /**
     * This get call searches for all accounts of an economy under the provided amount
     * This call can produce high load!
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query under
     * @return          - a list of all found accounts
     */
    @Override
    public ResponseEntity<List<EconomyAccount>> getAccountsUnderAmount(int economyId, double amount) {
        try {
            return ResponseEntity.ok(accountProvider.getAccountsUnderAmount(economyId, amount));
        } catch (EconomyEntityNotFoundException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This get call searches for all accounts of an economy under the provided amount
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query under
     * @return        - a list of all found accounts
     */
    @Override
    public ResponseEntity<List<EconomyAccount>> getAccountsUnderAmount(Economy economy, double amount) {
        return ResponseEntity.ok(accountProvider.getAccountsUnderAmount(economy, amount));
    }
}
