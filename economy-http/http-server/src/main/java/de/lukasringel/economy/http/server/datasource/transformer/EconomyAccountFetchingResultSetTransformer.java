package de.lukasringel.economy.http.server.datasource.transformer;

import de.lukasringel.economy.api.exception.EconomyEntityNotFoundException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.provider.EconomyProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;

/**
 * This transformer is used to create EconomyAccount instances
 * To get the economy object we need an instance of our EconomyProvider
 */

@RequiredArgsConstructor
public class EconomyAccountFetchingResultSetTransformer implements MysqlConnection.ResultSetTransFormer<EconomyAccount> {

    private final EconomyProvider economyProvider;

    /**
     * Converts our database result set to an instance of EconomyAccount
     * To get an economy object we need to ask our economyProvider instance
     *
     * @param resultSet     - our database result set
     * @return              - an instance of EconomyAccount with the stored and loaded data
     */
    @Override
    @SneakyThrows
    public EconomyAccount transform(ResultSet resultSet) {

        // resolving our economy by the database id
        int economyId = resultSet.getInt("economyId");
        Economy economy = economyProvider.getEconomyById(economyId).orElseThrow(EconomyEntityNotFoundException::new);

        return new EconomyAccount(
                resultSet.getInt("id"),
                economy,
                resultSet.getDouble("amount")
        );
    }


}
