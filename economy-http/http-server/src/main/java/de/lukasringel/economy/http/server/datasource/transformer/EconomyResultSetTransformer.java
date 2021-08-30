package de.lukasringel.economy.http.server.datasource.transformer;

import de.lukasringel.economy.api.model.economy.Economy;
import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This transformer is used to create Economy instances
 */

public class EconomyResultSetTransformer implements MysqlConnection.ResultSetTransFormer<Economy> {

    /**
     * Converts our database result set to an instance of Economy
     *
     * @param resultSet - our database result set
     * @return - an instance of Economy with the stored data
     * @throws SQLException - if we encounter any sql errors
     */
    @Override
    public Economy transform(ResultSet resultSet) throws SQLException {
        return new Economy(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("startValue"),
                resultSet.getDouble("increaseMultiplier"),
                resultSet.getDouble("decreaseMultiplier")
        );
    }

}
