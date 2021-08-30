package de.lukasringel.economy.api.model.transaction;

/**
 * This record represents a transaction of an account
 *
 * @param id              - the unique database id of the transaction
 * @param accountId       - the related unique id of the account
 * @param amount          - the amount of the transaction
 * @param timestamp       - the timestamp of the transaction
 * @param comment         - sometimes transactions need comments
 * @param transactionType - the type of the transaction
 */

public record Transaction(int id,
                          int accountId,
                          double amount,
                          long timestamp,
                          String comment,
                          TransactionType transactionType) {

    /**
     * This method just provides better readability to the code
     *
     * @return true if the value of comment isn't null
     */
    public boolean hasComment() {
        return comment != null;
    }

}
