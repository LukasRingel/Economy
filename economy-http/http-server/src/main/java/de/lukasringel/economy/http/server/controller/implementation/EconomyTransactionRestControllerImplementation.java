package de.lukasringel.economy.http.server.controller.implementation;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import de.lukasringel.economy.api.provider.EconomyTransactionProvider;
import de.lukasringel.economy.http.server.controller.RestController;
import de.lukasringel.economy.http.server.controller.rest.EconomyTransactionRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class implements our EconomyRestController
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class EconomyTransactionRestControllerImplementation implements RestController<Transaction>, EconomyTransactionRestController {

    private final EconomyTransactionProvider transactionProvider;

    /**
     * This post call saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     */
    @Override
    public ResponseEntity<Transaction> createTransaction(int accountId, double amount, TransactionType transactionType) {
        log.info("Successfully created transaction " + transactionType.name() + " account " + accountId + " with amount " + amount);
        return ResponseEntity.ok(transactionProvider.createTransaction(accountId, amount, transactionType));
    }

    /**
     * This post call saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     * @param comment         - the comment for the transaction
     */
    @Override
    public ResponseEntity<Transaction> createTransaction(int accountId, double amount, TransactionType transactionType, String comment) {
        log.info("Successfully created transaction " + transactionType.name() + " account " + accountId + " with amount " + amount);
        log.info("Comment -> " + comment);
        return ResponseEntity.ok(transactionProvider.createTransaction(accountId, amount, transactionType, comment));
    }

    /**
     * This get call searches for all transactions of the provided account
     *
     * @param accountId - the target account
     * @return          - a list with all transactions
     */
    @Override
    public ResponseEntity<List<Transaction>> getAllTransactionsOfAccount(int accountId) {
        return ResponseEntity.ok(transactionProvider.getAllTransactionsOfAccount(accountId));
    }

    /**
     * This get call searches for the recent transactions (see limit) of the provided account
     *
     * @param accountId - the target account
     * @param limit     - the limit how many transactions we should provide
     * @return          - a list with all transactions
     */
    @Override
    public ResponseEntity<List<Transaction>> getRecentTransactionsOfAccount(int accountId, int limit) {
        return ResponseEntity.ok(transactionProvider.getRecentTransactionsOfAccount(accountId, limit));
    }

    /**
     * This get call searches for all transactions of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @return                - a list with all transactions
     */
    @Override
    public ResponseEntity<List<Transaction>> getAllTransactionsOfAccountOfType(int accountId, TransactionType transactionType) {
        return ResponseEntity.ok(transactionProvider.getAllTransactionsOfAccountOfType(accountId, transactionType));
    }

    /**
     * This get call searches for all transactions (see limit) of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param limit           - the limit how many transactions we should provide
     * @param transactionType - the target type of the transactions
     * @return - a list with all transactions
     */
    @Override
    public ResponseEntity<List<Transaction>> getRecentTransactionsOfAccountOfType(int accountId, int limit, TransactionType transactionType) {
        return ResponseEntity.ok(transactionProvider.getRecentTransactionsOfAccountOfType(accountId, limit, transactionType));
    }
}
