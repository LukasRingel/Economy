package de.lukasringel.economy.http.server.datasource.transformer;

import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import lombok.RequiredArgsConstructor;
import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This transformer is used to create EconomyAccount instances
 * We get our economy instance by our constructor
 */

@RequiredArgsConstructor
public class EconomyAccountResultSetTransformer implements MysqlConnection.ResultSetTransFormer<EconomyAccount> {

    private final Economy economy;

    /**
     * Converts our database result set to an instance of EconomyAccount
     *
     * @param resultSet     - our database result set
     * @return              - an instance of EconomyAccount with the stored data
     * @throws SQLException - if we encounter any sql errors
     */
    @Override
    public EconomyAccount transform(ResultSet resultSet) throws SQLException {
        return new EconomyAccount(
                resultSet.getInt("id"),
                economy,
                resultSet.getDouble("amount")
        );
    }

}
