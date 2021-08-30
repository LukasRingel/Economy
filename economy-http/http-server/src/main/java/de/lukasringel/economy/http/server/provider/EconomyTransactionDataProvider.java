package de.lukasringel.economy.http.server.provider;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import de.lukasringel.economy.api.provider.EconomyTransactionProvider;
import de.lukasringel.economy.http.server.datasource.implementation.TransactionDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EconomyTransactionDataProvider implements DataProvider, EconomyTransactionProvider {

    private final TransactionDataSource transactionDataSource;

    /**
     * This method saves a transaction without comment in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     */
    @Override
    public Transaction createTransaction(int accountId, double amount, TransactionType transactionType) {
        return createTransaction(accountId, amount, transactionType, null);
    }

    /**
     * This method saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     * @param comment         - the comment for the transaction
     */
    @Override
    public Transaction createTransaction(int accountId, double amount, TransactionType transactionType, String comment) {
        return transactionDataSource.createTransaction(accountId, amount, transactionType, comment);
    }

    /**
     * This method searches for all transactions of the provided account
     *
     * @param accountId - the target account
     * @return - a list with all transactions
     */
    @Override
    public List<Transaction> getAllTransactionsOfAccount(int accountId) {
        return transactionDataSource.getAllTransactionsOfAccountWithOutLimit(accountId);
    }

    /**
     * This method searches for the recent transactions (see limit) of the provided account
     *
     * @param accountId - the target account
     * @param limit     - the limit how many transactions we should provide
     * @return - a list with all transactions
     */
    @Override
    public List<Transaction> getRecentTransactionsOfAccount(int accountId, int limit) {
        return transactionDataSource.getAllTransactionsOfAccountWithLimit(accountId, limit);
    }

    /**
     * This method searches for all transactions of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @return - a list with all transactions
     */
    @Override
    public List<Transaction> getAllTransactionsOfAccountOfType(int accountId, TransactionType transactionType) {
        return transactionDataSource.getAllTransactionsOfAccountWithOutLimitByType(accountId, transactionType);
    }

    /**
     * This method searches for all transactions (see limit) of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param limit           - the limit how many transactions we should provide
     * @param transactionType - the target type of the transactions
     * @return - a list with all transactions
     */
    @Override
    public List<Transaction> getRecentTransactionsOfAccountOfType(int accountId, int limit, TransactionType transactionType) {
        return transactionDataSource.getAllTransactionsOfAccountWithLimitByType(accountId, limit, transactionType);
    }

}
