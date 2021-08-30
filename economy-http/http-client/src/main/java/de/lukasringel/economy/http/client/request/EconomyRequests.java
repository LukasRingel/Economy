package de.lukasringel.economy.http.client.request;

import de.lukasringel.economy.api.model.economy.Economy;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our economy rest client
 */

public interface EconomyRequests {

    /**
     * This post call creates a new economy in our database
     *
     * @param name       - the name the new economy
     * @param startValue - the start value of the new economy
     * @return           - our ResponseEntity
     */
    @POST("economy/create")
    Observable<Economy> createEconomy(@Query("name") String name,
                                      @Query("startValue") double startValue);

    /**
     * This get call searches for an economy with the provided id
     *
     * @param id       - the id we search for
     * @return         - our ResponseEntity
     */
    @GET("economy/by/id")
    Observable<Economy> getEconomyById(@Query("id") int id);

    /**
     * This get call searches for an economy with the provided name
     *
     * @param name       - the name we search for
     * @return           - our ResponseEntity
     */
    @GET("economy/by/name")
    Observable<Economy> getEconomyByName(@Query("name") String name);

    /**
     * This get call searches for economies with an increase multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @GET("economy/multiplier/increase")
    Observable<List<Economy>> getEconomiesWithIncreaseMultiplier();

    /**
     * This get call searches for economies with a decrease multiplier (not 1.0)
     *
     * @return - our ResponseEntity
     */
    @GET("economy/multiplier/increase")
    Observable<List<Economy>> getEconomiesWithDecreaseMultiplier();

}
