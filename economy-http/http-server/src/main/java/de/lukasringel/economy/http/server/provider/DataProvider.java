package de.lukasringel.economy.http.server.provider;

import org.springframework.context.ApplicationContext;

/**
 * This interface should be applied to all existing data providers
 */

public interface DataProvider {

    /**
     * This method gets called if the refresh console command was executed or the application bootstraps
     */
    default void refresh() {
    }

    /**
     * This method gets called if the refresh console command was executed or the application bootstraps
     *
     * @param applicationContext - the current spring application context
     */
    default void refresh(ApplicationContext applicationContext) {
    }

}
