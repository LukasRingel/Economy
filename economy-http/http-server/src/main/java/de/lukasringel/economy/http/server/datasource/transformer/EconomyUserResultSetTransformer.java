package de.lukasringel.economy.http.server.datasource.transformer;

import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This transformer is used to create EconomyUser instances
 */

public class EconomyUserResultSetTransformer implements MysqlConnection.ResultSetTransFormer<EconomyUserResultSetTransformer.TemporaryResult> {

    /**
     * Converts our database result set to an instance of EconomyUser
     *
     * @param resultSet - our database result set
     * @return - an instance of EconomyUser with the stored data
     * @throws SQLException - if we encounter any sql errors
     */
    @Override
    public TemporaryResult transform(ResultSet resultSet) throws SQLException {
        return new TemporaryResult(
                resultSet.getInt("userId"),
                resultSet.getBoolean("userSuspended"),
                resultSet.getLong("userCreatedAt"),
                resultSet.getInt("accountId"),
                resultSet.getInt("accountEconomyId"),
                resultSet.getDouble("accountWorth")
        );
    }

    /**
     * This record should only be used for this temporary purpose.
     * It represents one result row and gets grouped later
     *
     * @param userId           - the id of our user
     * @param userSuspended    - is our user suspended?
     * @param userCreatedAt    - when was our user created?
     * @param accountId        - the id of the current account
     * @param accountEconomyId - the id of the accounts economy
     * @param accountWorth     - the current worth of the account
     */
    public static record TemporaryResult(int userId,
                                         boolean userSuspended,
                                         long userCreatedAt,
                                         int accountId,
                                         int accountEconomyId,
                                         double accountWorth) {

    }

}
