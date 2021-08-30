package de.lukasringel.economy.http.server.provider;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.exception.EconomyEntityNotFoundException;
import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import de.lukasringel.economy.api.provider.EconomyAccountProvider;
import de.lukasringel.economy.http.server.datasource.implementation.EconomyAccountDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EconomyAccountDataProvider implements EconomyAccountProvider {

    private final EconomyDataProvider economyDataProvider;
    private final EconomyAccountDataSource accountDataSource;
    private final EconomyTransactionDataProvider transactionDataProvider;

    /**
     * This method creates a new account for an existing user
     *
     * @param userId    - the id of the user
     * @param economyId - the id of the economy
     * @return - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown if there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments
     */
    @Override
    public EconomyAccount createAccount(int userId, int economyId) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException {
        Economy economy = economyDataProvider.getEconomyById(economyId).orElseThrow(EconomyEntityNotFoundException::new);
        return createAccount(userId, economy);
    }

    /**
     * This method creates a new account for an existing user
     *
     * @param userId  - the id of the user
     * @param economy - the target economy
     * @return - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a fitting account of this user
     */
    @Override
    public EconomyAccount createAccount(int userId, Economy economy) throws EconomyEntityAlreadyExistsException {

        if (accountDataSource.doesUserHasAccountOfEconomy(userId, economy.id())) {
            throw new EconomyEntityAlreadyExistsException("There is already an account for user " + userId + " of economy " + economy.name());
        }

        return accountDataSource.createAccount(userId, economy);
    }

    /**
     * This method creates a new account for an existing user
     *
     * @param user      - the target user
     * @param economyId - the id of the economy
     * @return - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a fitting account of this user
     * @throws EconomyEntityNotFoundException      - thrown if there is no entity for one of our arguments
     */
    @Override
    public EconomyAccount createAccount(EconomyUser user, int economyId) throws EconomyEntityAlreadyExistsException, EconomyEntityNotFoundException {
        Economy economy = economyDataProvider.getEconomyById(economyId).orElseThrow(EconomyEntityNotFoundException::new);
        return createAccount(user.id(), economy);
    }

    /**
     * This method creates a new account for an existing user
     *
     * @param user    - the target user
     * @param economy - the target economy
     * @return - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a fitting account of this user
     */
    @Override
    public EconomyAccount createAccount(EconomyUser user, Economy economy) throws EconomyEntityAlreadyExistsException {
        return createAccount(user.id(), economy);
    }

    /**
     * This method increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @Override
    public EconomyAccount increaseAccountWorth(EconomyAccount economyAccount, double amount) {
        return increaseAccountWorth(economyAccount, amount, null);
    }

    /**
     * This method increases the worth of the provided account by the provided amount
     * It also saves a comment to the transaction
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @Override
    public EconomyAccount increaseAccountWorth(EconomyAccount economyAccount, double amount, String comment) {
        //calculating our new account worth
        double worth = economyAccount.amount() + amount;

        //save the new worth
        accountDataSource.updateAccountWorth(economyAccount.id(), worth);

        //save the transaction
        transactionDataProvider.createTransaction(economyAccount.id(), amount, TransactionType.INCREASE, comment);

        return new EconomyAccount(economyAccount.id(), economyAccount.economy(), worth);
    }

    /**
     * This method decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @Override
    public EconomyAccount decreaseAccountWorth(EconomyAccount economyAccount, double amount) {
        return decreaseAccountWorth(economyAccount, amount, null);
    }

    /**
     * This method decreases the worth of the provided account by the provided amount
     * It also saves a comment to the transaction
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @Override
    public EconomyAccount decreaseAccountWorth(EconomyAccount economyAccount, double amount, String comment) {

        //calculating our new account worth
        double worth = economyAccount.amount() - amount;

        //save the new worth
        accountDataSource.updateAccountWorth(economyAccount.id(), worth);

        //save the transaction
        transactionDataProvider.createTransaction(economyAccount.id(), amount, TransactionType.DECREASE, comment);

        return new EconomyAccount(economyAccount.id(), economyAccount.economy(), worth);

    }

    /**
     * This method searches for an account with the provided id
     *
     * @param id - the id of the account
     * @return - an optional of the account since we don't know if an account with this id exists
     */
    @Override
    public Optional<EconomyAccount> getAccountById(int id) {
        return Optional.of(accountDataSource.getAccountById(id, economyDataProvider));
    }

    /**
     * This method searches for an account with the provided id
     *
     * @param id - the id of the account
     * @return - the account
     * @throws EconomyEntityNotFoundException - if there is no account with the provided id
     */
    @Override
    public EconomyAccount getAccountByIdOrThrow(int id) throws EconomyEntityNotFoundException {
        return getAccountById(id).orElseThrow(EconomyEntityNotFoundException::new);
    }

    /**
     * This method searches for all accounts of the provided economy by id
     *
     * @param id - the id of the target economy
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsOfEconomy(int id) throws EconomyEntityNotFoundException {
        Economy economy = economyDataProvider.getEconomyById(id).orElseThrow(EconomyEntityNotFoundException::new);
        return getAccountsOfEconomy(economy);
    }

    /**
     * This method searches for all accounts of the provided economy by an instance
     *
     * @param economy - the target economy
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsOfEconomy(Economy economy) {
        return accountDataSource.getAccountsOfEconomy(economy);
    }

    /**
     * This method searches for all accounts of an economy above the provided amount
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query above
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsAboveAmount(int economyId, double amount) throws EconomyEntityNotFoundException {
        Economy economy = economyDataProvider.getEconomyById(economyId).orElseThrow(EconomyEntityNotFoundException::new);
        return getAccountsAboveAmount(economy, amount);
    }

    /**
     * This method searches for all accounts of an economy above the provided amount
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query above
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsAboveAmount(Economy economy, double amount) {
        return accountDataSource.getAccountsAboveAmount(economy, amount);
    }

    /**
     * This method searches for all accounts of an economy under the provided amount
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query under
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsUnderAmount(int economyId, double amount) throws EconomyEntityNotFoundException {
        Economy economy = economyDataProvider.getEconomyById(economyId).orElseThrow(EconomyEntityNotFoundException::new);
        return getAccountsUnderAmount(economy, amount);
    }

    /**
     * This method searches for all accounts of an economy under the provided amount
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query under
     * @return - a list of all found accounts
     */
    @Override
    @HighLoad
    public List<EconomyAccount> getAccountsUnderAmount(Economy economy, double amount) {
        return accountDataSource.getAccountsUnderAmount(economy, amount);
    }
}
