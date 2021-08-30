package de.lukasringel.economy.api.exception;

import lombok.NoArgsConstructor;

/**
 * This exception gets thrown if somebody tries to create a new entity but there is already one
 */

@NoArgsConstructor
public class EconomyEntityAlreadyExistsException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EconomyEntityAlreadyExistsException(String message) {
        super("Error while creating new entity: " + message);
    }

}
