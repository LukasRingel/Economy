package de.lukasringel.economy.api.exception;

import lombok.NoArgsConstructor;

/**
 * This exception gets thrown if somebody queries for an entity that doesn't exist
 */

@NoArgsConstructor
public class EconomyEntityNotFoundException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EconomyEntityNotFoundException(String message) {
        super(message);
    }

}
