package de.lukasringel.economy.api.provider;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;

import java.util.List;

/**
 * This interface provides methods to work with transactions or just query them
 */

public interface EconomyTransactionProvider {

    /**
     * This method saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     */
    Transaction createTransaction(int accountId, double amount, TransactionType transactionType);

    /**
     * This method saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     * @param comment         - the comment for the transaction
     */
    Transaction createTransaction(int accountId, double amount, TransactionType transactionType, String comment);

    /**
     * This method searches for all transactions of the provided account
     *
     * @param accountId - the target account
     * @return          - a list with all transactions
     */
    List<Transaction> getAllTransactionsOfAccount(int accountId);

    /**
     * This method searches for the recent transactions (see limit) of the provided account
     *
     * @param accountId - the target account
     * @param limit     - the limit how many transactions we should provide
     * @return          - a list with all transactions
     */
    List<Transaction> getRecentTransactionsOfAccount(int accountId, int limit);

    /**
     * This method searches for all transactions of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @return                - a list with all transactions
     */
    List<Transaction> getAllTransactionsOfAccountOfType(int accountId, TransactionType transactionType);

    /**
     * This method searches for all transactions (see limit) of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @param limit           - the limit how many transactions we should provide
     * @return                - a list with all transactions
     */
    List<Transaction> getRecentTransactionsOfAccountOfType(int accountId, int limit, TransactionType transactionType);

}
