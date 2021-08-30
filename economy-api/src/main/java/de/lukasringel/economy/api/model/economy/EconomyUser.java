package de.lukasringel.economy.api.model.economy;

import de.lukasringel.economy.api.model.ExternalIdentifier;

import java.util.List;
import java.util.Optional;

/**
 * This record represents a user with all accounts and information
 *
 * @param id                  - the unique database id of the user
 * @param externalIdentifiers - all external identifiers of the user (discord id, teamspeak id, minecraft uuid...)
 * @param accounts            - all accounts of the user
 * @param suspended           - is the account suspended / deactivated?
 * @param createdAt           - timestamp in millis when the account was created
 *
 */

public record EconomyUser(int id,
                          List<ExternalIdentifier> externalIdentifiers,
                          List<EconomyAccount> accounts,
                          boolean suspended,
                          long createdAt) {

    /**
     * This method searches in accounts list for an account with the corresponding economy by id
     *
     * @param id - id of the target economy
     * @return - an optional of the account since we don't know if the user owns an account of this economy
     */
    public Optional<EconomyAccount> getAccountByEconomyId(int id) {
        return accounts.stream().filter(economyAccount -> economyAccount.id() == id).findFirst();
    }

    /**
     * This method searches in accounts list for an account with the corresponding economy by an instance of economy
     *
     * @param economy - instance of the target economy
     * @return - an optional of the account since we don't know if the user owns an account of this economy
     */
    public Optional<EconomyAccount> getAccountByEconomyObject(Economy economy) {
        return accounts.stream().filter(economyAccount -> economyAccount.id() == economy.id()).findFirst();
    }

    /**
     * his method searches in accounts list for an account with the corresponding economy by id
     *
     * @param id - id of the target economy
     * @return - true if there is a match / false if not
     */
    public boolean hasAccountForEconomy(int id) {
        return accounts.stream().anyMatch(economyAccount -> economyAccount.id() == id);
    }

    /**
     * his method searches in accounts list for an account with the corresponding economy by an instance of economy
     *
     * @param economy - instance of the target economy
     * @return - true if there is a match / false if not
     */
    public boolean hasAccountForEconomy(Economy economy) {
        return accounts.stream().anyMatch(economyAccount -> economyAccount.id() == economy.id());
    }

}
