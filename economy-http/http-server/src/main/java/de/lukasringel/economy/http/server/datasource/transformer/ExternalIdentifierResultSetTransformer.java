package de.lukasringel.economy.http.server.datasource.transformer;

import de.lukasringel.economy.api.model.ExternalIdentifier;
import me.xkuyax.utils.mysql.MysqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This transformer is used to create ExternalIdentifier instances
 */

public class ExternalIdentifierResultSetTransformer implements MysqlConnection.ResultSetTransFormer<ExternalIdentifier> {

    /**
     * Converts our database result set to an instance of ExternalIdentifier
     *
     * @param resultSet - our database result set
     * @return - an instance of ExternalIdentifier with the stored data
     * @throws SQLException - if we encounter any sql errors
     */
    @Override
    public ExternalIdentifier transform(ResultSet resultSet) throws SQLException {
        return new ExternalIdentifier(
                resultSet.getInt("id"),
                resultSet.getString("key"),
                resultSet.getString("value"),
                resultSet.getBoolean("active"),
                resultSet.getLong("createdAt")
        );
    }

}
