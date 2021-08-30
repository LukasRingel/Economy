package de.lukasringel.economy.http.server.datasource.transformer;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This transformer is used to create Transaction instances
 */

public class EconomyTransactionResultSetTransformer implements MysqlConnection.ResultSetTransFormer<Transaction> {

    /**
     * Converts our database result set to an instance of Transaction
     *
     * @param resultSet     - our database result set
     * @return              - an instance of Transaction with the stored data
     * @throws SQLException - if we encounter any sql errors
     */
    @Override
    public Transaction transform(ResultSet resultSet) throws SQLException {
        return new Transaction(
                resultSet.getInt("id"),
                resultSet.getInt("accountId"),
                resultSet.getDouble("amount"),
                resultSet.getLong("timestamp"),
                resultSet.getString("comment"),
                getTransactionTypeById(resultSet.getInt("type"))
        );
    }

    /**
     * Resolves our TransactionType by a provided id
     * Since our id is just the ordinal this is very easy
     *
     * @param id - the id of the transaction type
     * @return   - our enum representation
     */
    private TransactionType getTransactionTypeById(int id) {
        return TransactionType.values()[id];
    }

}
