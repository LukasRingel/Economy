package de.lukasringel.economy.http.server.datasource.implementation;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.http.server.datasource.DataSource;
import de.lukasringel.economy.http.server.datasource.transformer.EconomyResultSetTransformer;
import lombok.RequiredArgsConstructor;
import me.xkuyax.utils.mysql.MysqlConnection;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This data source provides data for our local EconomyProvider
 */

@Component
@RequiredArgsConstructor
public class EconomyDataSource implements DataSource {

    private static final EconomyResultSetTransformer ECONOMY_TRANSFORMER = new EconomyResultSetTransformer();

    private final MysqlConnection mysqlConnection;

    /**
     * This method searches for economies in our database
     *
     * @return - a list with all stored economies
     */
    public List<Economy> getAllEconomies() {
        return mysqlConnection.queryList(
                "select * from economy_economies;",
                emptyFiller(),
                ECONOMY_TRANSFORMER
        );
    }

    /**
     * This method creates a new economy in database
     *
     * @param name       - the name of the economy
     * @param startValue - the default worth of every new account
     * @return - our new economy
     * @throws EconomyEntityAlreadyExistsException - if there is already an economy with this name
     */
    public Economy createEconomy(String name, double startValue) throws EconomyEntityAlreadyExistsException {
        int id = mysqlConnection.keyInsert(
                "insert into economy_economies(name, startValue) values (?,?);",
                preparedStatement -> {
                    preparedStatement.setString(1, name);
                    preparedStatement.setDouble(2, startValue);
                }
        );

        // the id is 0 if there is already an entry in our table
        if (id == 0) {
            throw new EconomyEntityAlreadyExistsException("There is already an economy with the name " + name +
                    " in out database but not in our local storage. Type 'refresh' to refresh the local cache");
        }

        return new Economy(id, name, startValue, 1.0, 1.0);
    }

}
