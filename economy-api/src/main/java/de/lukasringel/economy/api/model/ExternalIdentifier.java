package de.lukasringel.economy.api.model;

/**
 * This record represents an external identifier of a user
 *
 * @param id        - the unique database id of the identifier
 * @param key       - the query key of the identifier (the identifier himself)
 * @param value     - the value of the identifier
 * @param active    - is the identifier still valid?
 * @param createdAt - timestamp in millis when the account was created
 *
 */

public record ExternalIdentifier(int id,
                                 String key,
                                 String value,
                                 boolean active,
                                 long createdAt) {

    /**
     * This method just provides readability to our code
     *
     * @param check - the value we should check for
     * @return      - if check equals our value
     */
    public boolean hasValue(String check) {
        return value.equalsIgnoreCase(check);
    }

}
