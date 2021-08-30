package de.lukasringel.economy.http.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This interface should be applied to all existing rest controllers
 */

public interface RestController<T> {

    /**
     * This method should be used to return a ResponseEntity with error code 500 to the client
     *
     * @return - our 500 ResponseEntity
     */
    default ResponseEntity<T> errorResponseEntity() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
