package de.lukasringel.economy.api.model.economy;

/**
 * This record represents an account for an economy of a user
 *
 * @param id      - the unique database id of the account
 * @param economy - the related economy of the account
 * @param amount  - the current worth of the account
 */

public record EconomyAccount(int id,
                             Economy economy,
                             double amount) {

    /**
     * This method just provides readability to our code
     *
     * @param limit - the limit for which we should check for
     * @return - if the account has a bigger amount than the provided limit
     */
    public boolean hasMoreThan(double limit) {
        return amount > limit;
    }

    /**
     * This method just provides readability to our code
     *
     * @param limit - the limit for which we should check for
     * @return - if the account has a lower amount than the provided limit
     */
    public boolean hasLessThan(double limit) {
        return amount < limit;
    }

}
