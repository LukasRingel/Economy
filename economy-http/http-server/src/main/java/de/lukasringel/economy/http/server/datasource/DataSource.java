package de.lukasringel.economy.http.server.datasource;

import me.xkuyax.utils.mysql.MysqlConnection;

/**
 * This interface should be applied to all existing data sources
 * It provides some useful methods
 */

public interface DataSource {

    /**
     * This method just returns an empty PreparedStatementFiller to provide readability to code
     *
     * @return - an empty PreparedStatementFiller
     */
    default MysqlConnection.PreparedStatementFiller emptyFiller() {
        return preparedStatement -> {};
    }

    /**
     * This method just fills our preparedStatement with an int at first position
     *
     * @return - a filled PreparedStatementFiller
     */
    default MysqlConnection.PreparedStatementFiller firstPositionIntegerFiller(int integer) {
        return preparedStatement -> preparedStatement.setInt(1, integer);
    }

    /**
     * This method just fills our preparedStatement with a long at first position
     *
     * @return - a filled PreparedStatementFiller
     */
    default MysqlConnection.PreparedStatementFiller firstPositionLongFiller(long targetLong) {
        return preparedStatement -> preparedStatement.setLong(1, targetLong);
    }

    /**
     * This method just fills our preparedStatement with some strings
     *
     * @return - a filled PreparedStatementFiller
     */
    default MysqlConnection.PreparedStatementFiller flexStringFiller(String... strings) {
        return preparedStatement -> {
            for (int i = 0; i < strings.length; i++) {
                preparedStatement.setString(i, strings[i]);
            }
        };
    }

    /**
     * This method just fills our preparedStatement with some integers
     *
     * @return - a filled PreparedStatementFiller
     */
    default MysqlConnection.PreparedStatementFiller flexIntegerFiller(Integer... integers) {
        return preparedStatement -> {
            for (int i = 0; i < integers.length; i++) {
                preparedStatement.setInt(i, integers[i]);
            }
        };
    }

}
