package de.lukasringel.economy.http.client.request;

import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our account rest client
 */

public interface EconomyAccountRequests {

    /**
     * This post call creates a new account for an existing user
     *
     * @param userId    - the id of the user
     * @param economyId - the id of the economy
     * @return          - the new created account
     */
    @POST("account/create")
    Observable<EconomyAccount> createAccount(@Query("userId") int userId,
                                             @Query("economyId") int economyId);

    /**
     * This post call creates a new account for an existing user
     *
     * @param userId    - the id of the user
     * @param economy   - the target economy
     * @return          - the new created account
     */
    @POST("account/create")
    Observable<EconomyAccount> createAccount(@Query("userId") int userId,
                                                 @Query("economy") Economy economy);

    /**
     * This post call creates a new account for an existing user
     *
     * @param user      - the target user
     * @param economyId - the target economyId
     * @return          - the new created account
     */
    @POST("account/create")
    Observable<EconomyAccount> createAccount(@Query("user") EconomyUser user,
                                                 @Query("economyId") int economyId);

    /**
     * This post call creates a new account for an existing user
     *
     * @param user    - the target user
     * @param economy - the target economy
     * @return        - the new created account
     */
    @POST("account/create")
    Observable<EconomyAccount> createAccount(@Query("user") EconomyUser user,
                                                 @Query("economy") Economy economy);

    /**
     * This post call increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @POST("account/worth/increase")
    Observable<EconomyAccount> increaseAccountWorth(@Query("account") EconomyAccount economyAccount,
                                                        @Query("amount") double amount);

    /**
     * This post call increases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @POST("account/worth/increase")
    Observable<EconomyAccount> increaseAccountWorth(@Query("account") EconomyAccount economyAccount,
                                                        @Query("amount") double amount,
                                                        @Query("comment") String comment);

    /**
     * This post call decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     */
    @POST("account/worth/decrease")
    Observable<EconomyAccount> decreaseAccountWorth(@Query("account") EconomyAccount economyAccount,
                                                        @Query("amount") double amount);

    /**
     * This post call decreases the worth of the provided account by the provided amount
     *
     * @param economyAccount - the target account
     * @param amount         - the target amount
     * @param comment        - the comment for the transaction
     */
    @POST("account/worth/decrease")
    Observable<EconomyAccount> decreaseAccountWorth(@Query("account") EconomyAccount economyAccount,
                                                        @Query("amount") double amount,
                                                        @Query("comment") String comment);

    /**
     * This get call searches for an account with the provided id
     *
     * @param accountId - the id of the account
     * @return          - an optional of the account since we don't know if an account with this id exists
     */
    @GET("account/get/single")
    Observable<EconomyAccount> getAccountById(@Query("accountId") int accountId);

    /**
     * This get call searches for all accounts of the provided economy by id
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @return        - a list of all found accounts
     */
    @HighLoad
    @GET("account/get/multi/economy")
    Observable<List<EconomyAccount>> getAccountsOfEconomy(@Query("economy") Economy economy);

    /**
     * This get call searches for all accounts of an economy above the provided amount
     * This call can produce high load!
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query above
     * @return          - a list of all found accounts
     */
    @HighLoad
    @GET("account/get/multi/amount/above")
    Observable<List<EconomyAccount>> getAccountsAboveAmount(@Query("economyId") int economyId,
                                                                @Query("amount") double amount);

    /**
     * This get call searches for all accounts of an economy above the provided amount
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query above
     * @return        - a list of all found accounts
     */
    @HighLoad
    @GET("account/get/multi/amount/above")
    Observable<List<EconomyAccount>> getAccountsAboveAmount(@Query("economy") Economy economy,
                                                                @Query("amount") double amount);

    /**
     * This get call searches for all accounts of an economy under the provided amount
     * This call can produce high load!
     *
     * @param economyId - the id of the target economy
     * @param amount    - the amount which we query under
     * @return          - a list of all found accounts
     */
    @HighLoad
    @GET("account/get/multi/amount/under")
    Observable<List<EconomyAccount>> getAccountsUnderAmount(@Query("economyId") int economyId,
                                                                @Query("amount") double amount);

    /**
     * This get call searches for all accounts of an economy under the provided amount
     * This call can produce high load!
     *
     * @param economy - the target economy
     * @param amount  - the amount which we query under
     * @return        - a list of all found accounts
     */
    @HighLoad
    @GET("account/get/multi/amount/under")
    Observable<List<EconomyAccount>> getAccountsUnderAmount(@Query("economy") Economy economy,
                                                                @Query("amount") double amount);

}
