package de.lukasringel.economy.http.server.datasource.implementation;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.provider.EconomyProvider;
import de.lukasringel.economy.http.server.datasource.DataSource;
import de.lukasringel.economy.http.server.datasource.transformer.EconomyAccountFetchingResultSetTransformer;
import de.lukasringel.economy.http.server.datasource.transformer.EconomyAccountResultSetTransformer;
import lombok.RequiredArgsConstructor;
import me.xkuyax.utils.mysql.MysqlConnection;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This data source provides data for our local EconomyAccountProvider
 */

@Component
@RequiredArgsConstructor
public class EconomyAccountDataSource implements DataSource {

    private final MysqlConnection mysqlConnection;

    /**
     * This method creates a new account in database
     *
     * @param userId  - the id of the user
     * @param economy - the target economy
     * @return - our new account
     * @throws EconomyEntityAlreadyExistsException - if there is already an account of this user for this economy
     */
    public EconomyAccount createAccount(int userId, Economy economy) throws EconomyEntityAlreadyExistsException {
        int accountId = mysqlConnection.keyInsert("insert into economy_accounts(userId, economyId, amount) values (?,?,?);", preparedStatement -> {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, economy.id());
            preparedStatement.setDouble(3, economy.startValue());
        });

        // the accountId is 0 if there is already an entry in our table
        if (accountId == 0) {
            throw new EconomyEntityAlreadyExistsException("There is already an account of user " + userId +
                    " for economy " + economy.name() + " in out database but not in our local storage. " +
                    "Type 'refresh' to refresh the local cache");
        }

        return new EconomyAccount(accountId, economy, economy.startValue());
    }

    /**
     * This method loads the account with the target id from our database
     *
     * @param accountId - our if of the account
     * @param economy   - our target economy
     * @return - our stored account or null if there is no account with this id
     */
    public EconomyAccount getAccountById(int accountId, Economy economy) {
        return mysqlConnection.query(
                "select * from economy_users_accounts where id = ?;",
                firstPositionIntegerFiller(accountId),
                new EconomyAccountResultSetTransformer(economy)
        );
    }

    /**
     * This method loads the account with the target id from our database
     *
     * @param accountId       - our if of the account
     * @param economyProvider - some instance of EconomyProvider
     * @return - our stored account or null if there is no account with this id
     */
    public EconomyAccount getAccountById(int accountId, EconomyProvider economyProvider) {
        return mysqlConnection.query(
                "select * from economy_users_accounts where id = ?;",
                firstPositionIntegerFiller(accountId),
                new EconomyAccountFetchingResultSetTransformer(economyProvider)
        );
    }

    /**
     * This method checks if there is an account with the provided data in our database
     *
     * @param userId    - our if of the user
     * @param economyId - our if of the economy
     * @return - if there is a fitting entry in our database
     */
    public boolean doesUserHasAccountOfEconomy(int userId, int economyId) {
        return mysqlConnection.hasEntries(
                "select * from economy_users_accounts where userId = ? and economyId = ?;",
                flexIntegerFiller(userId, economyId)
        );
    }

    /**
     * This method loads all accounts with a worth above the provided amount of the target economy from our database
     *
     * @param economy - our target economy
     * @param amount  - our target amount
     * @return - a list with all accounts we found
     */
    public List<EconomyAccount> getAccountsAboveAmount(Economy economy, double amount) {
        EconomyAccountResultSetTransformer resultSetTransformer = new EconomyAccountResultSetTransformer(economy);
        return mysqlConnection.queryList(
                "select * from economy_users_accounts where economyId = ? and amount > ?;",
                preparedStatement -> {
                    preparedStatement.setInt(1, economy.id());
                    preparedStatement.setDouble(2, amount);
                },
                resultSetTransformer
        );
    }

    /**
     * This method loads all accounts with a worth under the provided amount of the target economy from our database
     *
     * @param economy - our target economy
     * @param amount  - our target amount
     * @return - a list with all accounts we found
     */
    public List<EconomyAccount> getAccountsUnderAmount(Economy economy, double amount) {
        EconomyAccountResultSetTransformer resultSetTransformer = new EconomyAccountResultSetTransformer(economy);
        return mysqlConnection.queryList(
                "select * from economy_users_accounts where economyId = ? and amount < ?;",
                preparedStatement -> {
                    preparedStatement.setInt(1, economy.id());
                    preparedStatement.setDouble(2, amount);
                },
                resultSetTransformer
        );
    }

    /**
     * This method loads all accounts with the target economy from our database
     *
     * @param economy - our target economy
     * @return - a list with all accounts of the economy
     */
    public List<EconomyAccount> getAccountsOfEconomy(Economy economy) {
        EconomyAccountResultSetTransformer resultSetTransformer = new EconomyAccountResultSetTransformer(economy);
        return mysqlConnection.queryList(
                "select * from economy_users_accounts where economyId = ?;",
                firstPositionIntegerFiller(economy.id()),
                resultSetTransformer
        );
    }

    /**
     * This method updates the worth of an account in our database
     *
     * @param accountId - the id of our target account
     * @param worth     - the new worth of our account
     */
    public void updateAccountWorth(int accountId, double worth) {
        mysqlConnection.prepare("update economy_users_accounts set amount = ? where accountId = ?;",
                preparedStatement -> {
                    preparedStatement.setDouble(1, worth);
                    preparedStatement.setInt(2, accountId);
                }
        );
    }

}
