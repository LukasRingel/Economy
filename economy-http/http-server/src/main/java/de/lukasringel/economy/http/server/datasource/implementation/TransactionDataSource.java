package de.lukasringel.economy.http.server.datasource.implementation;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import de.lukasringel.economy.http.server.datasource.DataSource;
import de.lukasringel.economy.http.server.datasource.transformer.EconomyTransactionResultSetTransformer;
import lombok.RequiredArgsConstructor;
import me.xkuyax.utils.mysql.MysqlConnection;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This data source provides data for our local TransactionProvider
 */

@Component
@RequiredArgsConstructor
public class TransactionDataSource implements DataSource {

    private static final EconomyTransactionResultSetTransformer TRANSACTION_TRANSFORMER = new EconomyTransactionResultSetTransformer();

    private final MysqlConnection mysqlConnection;

    /**
     * This method saves a new transaction in our database
     *
     * @param accountId       - the id if the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     * @param comment         - some comments for the transaction (can be null)
     * @return                - an instance of Transaction with our data
     */
    public Transaction createTransaction(int accountId, double amount, TransactionType transactionType, String comment) {
        long timestamp = System.currentTimeMillis();
        int transactionKey = mysqlConnection.keyInsert(
                        "insert into economy_transactions(accountId, amount, timestamp, comment, type) values (?,?,?,?,?);",
                preparedStatement -> {
                    preparedStatement.setInt(1, accountId);
                    preparedStatement.setDouble(2, amount);
                    preparedStatement.setLong(3, timestamp);
                    preparedStatement.setString(4, comment);
                    preparedStatement.setInt(5, transactionType.ordinal());
                });

        return new Transaction(transactionKey, accountId, amount, timestamp, comment, transactionType);
    }

    /**
     * This method searches for all transactions of an account
     *
     * @param accountId - the if of the target account
     * @return          - a list with all transactions
     */
    public List<Transaction> getAllTransactionsOfAccountWithOutLimit(int accountId) {
        return mysqlConnection.queryList(
                "select * from economy_transactions where accountId = ?;",
                firstPositionIntegerFiller(accountId),
                TRANSACTION_TRANSFORMER
        );
    }

    /**
     * This method searches for the last transactions of an account
     *
     * @param accountId - the if of the target account
     * @param limit     - the maximal amount of transactions we want to get
     * @return          - a list with found transactions
     */
    public List<Transaction> getAllTransactionsOfAccountWithLimit(int accountId, int limit) {
        return mysqlConnection.queryList(
                "select * from economy_transactions where accountId = ? order by timestamp desc limit ?;",
                flexIntegerFiller(accountId, limit),
                TRANSACTION_TRANSFORMER
        );
    }

    /**
     * This method searches for all transactions of an account filtered by their type
     *
     * @param accountId       - the if of the target account
     * @param transactionType - the target type of the transactions
     * @return                - a list with all transactions of the provided type
     */
    public List<Transaction> getAllTransactionsOfAccountWithOutLimitByType(int accountId, TransactionType transactionType) {
        return mysqlConnection.queryList(
                "select * from economy_transactions where accountId = ? and type = ?;",
                flexIntegerFiller(accountId, transactionType.ordinal()),
                TRANSACTION_TRANSFORMER
        );
    }

    /**
     * This method searches for the last transactions of an account
     *
     * @param accountId       - the if of the target account
     * @param limit           - the maximal amount of transactions we want to get
     * @param transactionType - the target type of the transactions
     * @return                - a list with found transactions
     */
    public List<Transaction> getAllTransactionsOfAccountWithLimitByType(int accountId, int limit, TransactionType transactionType) {
        return mysqlConnection.queryList(
                "select * from economy_transactions where accountId = ? and type = ? order by timestamp desc limit ?;",
                flexIntegerFiller(accountId, transactionType.ordinal(), limit),
                TRANSACTION_TRANSFORMER
        );
    }

}
