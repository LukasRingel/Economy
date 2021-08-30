package de.lukasringel.economy.api.provider;

import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.exception.EconomyEntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * This interface provides methods to work with accounts or just query them
 */

public interface EconomyAccountProvider {

    /**
     * This method creates a new account for an existing user
     *
     * @param userId    - the id of the player
     * @param economyId - the id of the economy
     * @return          - the new created account
     *
     * @throws EconomyEntityAlreadyExistsException - thrown if there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments
     */
    EconomyAccount createAccount(int userId, int economyId) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException;

    /**
     * This method creates a new account for an existing user
     *
     * @param userId  - the id of the player
     * @param economy - the target economy
     * @return        - the new created account
     *
     * @throws EconomyEntityAlreadyExistsException - thrown if there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments
     */
    EconomyAccount createAccount(int userId, Economy economy) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException;

    /**
     * This method creates a new account for an existing user
     *
     * @param user      - the target user
     * @param economyId - the id of the economy
     * @return          - the new created account
     *
     * @throws EconomyEntityAlreadyExistsException - thrown if there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments
     */
    EconomyAccount createAccount(EconomyUser user, int economyId) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException;

    /**
     * This method creates a new account for an existing user
     *
     * @param user    - the target user
     * @param economy - the target economy
     * @return        - the new created account
     *
     * @throws EconomyEntityAlreadyExistsException - thrown if there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments  
     */
    EconomyAccount createAccount(EconomyUser user, Economy economy) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException;

    /**
     * This method increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    EconomyAccount increaseAccountWorth(EconomyAccount economyAccount, double amount);

    /**
     * This method increases the worth of the provided account by the provided amount
     * It also saves a comment to the transaction
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    EconomyAccount increaseAccountWorth(EconomyAccount economyAccount, double amount, String comment);

    /**
     * This method decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    EconomyAccount decreaseAccountWorth(EconomyAccount economyAccount, double amount);

    /**
     * This method decreases the worth of the provided account by the provided amount
     * It also saves a comment to the transaction
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    EconomyAccount decreaseAccountWorth(EconomyAccount economyAccount, double amount, String comment);

    /**
     * This method searches for an account with the provided id
     *
     * @param id - the id of the account
     * @return   - an optional of the account since we don't know if an account with this id exists
     */
    Optional<EconomyAccount> getAccountById(int id);

    /**
     * This method searches for an account with the provided id
     *
     * @param id - the id of the account
     * @return   - the account
     * @throws EconomyEntityNotFoundException - if there is no account with the provided id
     */
    EconomyAccount getAccountByIdOrThrow(int id) throws EconomyEntityNotFoundException;

    /**
     * This method searches for all accounts of the provided economy by id
     *
     * @param id - the id of the target economy
     * @return   - a list of all found accounts
     * @throws EconomyEntityNotFoundException - if there is no economy with the provided id
     */
    @HighLoad
    List<EconomyAccount> getAccountsOfEconomy(int id) throws EconomyEntityNotFoundException;

    /**
     * This method searches for all accounts of the provided economy by an instance
     *
     * @param economy - the target economy
     * @return        - a list of all found accounts
     */
    @HighLoad
    List<EconomyAccount> getAccountsOfEconomy(Economy economy);

    /**
     * This method searches for all accounts of an economy above the provided amount
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query above
     * @return          - a list of all found accounts
     */
    @HighLoad
    List<EconomyAccount> getAccountsAboveAmount(int economyId, double amount) throws EconomyEntityNotFoundException;

    /**
     * This method searches for all accounts of an economy above the provided amount
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query above
     * @return        - a list of all found accounts
     */
    @HighLoad
    List<EconomyAccount> getAccountsAboveAmount(Economy economy, double amount);

    /**
     * This method searches for all accounts of an economy under the provided amount
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query under
     * @return          - a list of all found accounts
     */
    @HighLoad
    List<EconomyAccount> getAccountsUnderAmount(int economyId, double amount) throws EconomyEntityNotFoundException;

    /**
     * This method searches for all accounts of an economy under the provided amount
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query under
     * @return        - a list of all found accounts
     */
    @HighLoad
    List<EconomyAccount> getAccountsUnderAmount(Economy economy, double amount);

}