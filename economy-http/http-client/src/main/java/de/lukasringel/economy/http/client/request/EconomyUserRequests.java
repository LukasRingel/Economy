package de.lukasringel.economy.http.client.request;

import de.lukasringel.economy.api.exception.EconomyEntityAlreadyExistsException;
import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our user rest client
 */

public interface EconomyUserRequests {

    /**
     * This post call creates a new user
     *
     * @param externalIdentifiers - related external identifiers (Format: key, value, key, value...)
     * @return                    - the new created account
     * @throws EconomyEntityAlreadyExistsException - thrown of there already is a user with this identifiers
     */
    @POST("user/create")
    Observable<EconomyUser> createUser(@Query("externalIdentifiers") String... externalIdentifiers) throws EconomyEntityAlreadyExistsException;

    /**
     * This post call searches for a user with the provided id
     *
     * @param id - the id of the user
     * @return   - an optional of the user since we don't know if a user with this id exists
     */
    @GET("user/by/id")
    Observable<EconomyUser> getUserById(@Query("id") int id);

    /**
     * This get call searches for a user with the provided identifier
     *
     * @param key   - the key of the identifier
     * @param query - the query of the identifier
     * @return      - an optional of the user since we don't know if there will be a match
     */
    @GET("user/by/identifier")
    Observable<EconomyUser> getUserByIdentifier(@Query("key") String key,
                                                    @Query("query") String query);

    /**
     * This get call searches for all suspended users
     *
     * @return - a list of all suspended users
     */
    @HighLoad
    @GET("user/suspended")
    Observable<List<EconomyUser>> getSuspendedUsers();

    /**
     * This get call searches for all users which were created before the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    @GET("user/created/before")
    Observable<List<EconomyUser>> getUsersCreatedBeforeTimeStamp(@Query("timestamp") long timestamp);

    /**
     * This get call searches for all users which were created after the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return          - a list with all found users
     */
    @HighLoad
    @GET("user/created/after")
    Observable<List<EconomyUser>> getUsersCreatedAfterTimeStamp(@Query("timestamp") long timestamp);

}
