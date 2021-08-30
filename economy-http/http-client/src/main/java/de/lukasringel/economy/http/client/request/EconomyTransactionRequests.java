package de.lukasringel.economy.http.client.request;

import de.lukasringel.economy.api.model.transaction.Transaction;
import de.lukasringel.economy.api.model.transaction.TransactionType;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * This interface represents our transaction rest client
 */

public interface EconomyTransactionRequests {

    /**
     * This post call saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     */
    @POST("transaction/create")
    Observable<Transaction> createTransaction(@Query("accountId") int accountId,
                                              @Query("amount") double amount,
                                              @Query("transactionType") TransactionType transactionType);

    /**
     * This post call saves a transaction in our database
     *
     * @param accountId       - the id of the target account
     * @param amount          - the amount of the transaction
     * @param transactionType - the type of the transaction
     * @param comment         - the comment for the transaction
     */
    @POST("transaction/create")
    Observable<Transaction> createTransaction(@Query("accountId") int accountId,
                                                  @Query("amount") double amount,
                                                  @Query("transactionType") TransactionType transactionType,
                                                  @Query("comment") String comment);

    /**
     * This get call searches for all transactions of the provided account
     *
     * @param accountId - the target account
     * @return          - a list with all transactions
     */
    @GET("transaction/account/all/allTime")
    Observable<List<Transaction>> getAllTransactionsOfAccount(@Query("accountId") int accountId);

    /**
     * This get call searches for the recent transactions (see limit) of the provided account
     *
     * @param accountId - the target account
     * @param limit     - the limit how many transactions we should provide
     * @return          - a list with all transactions
     */
    @GET("transaction/account/all/recent")
    Observable<List<Transaction>> getRecentTransactionsOfAccount(@Query("accountId") int accountId,
                                                                     @Query("limit") int limit);

    /**
     * This get call searches for all transactions of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @return                - a list with all transactions
     */
    @GET("transaction/account/type/allTime")
    Observable<List<Transaction>> getAllTransactionsOfAccountOfType(@Query("accountId") int accountId,
                                                                        @Query("transactionType") TransactionType transactionType);

    /**
     * This get call searches for all transactions (see limit) of the provided account filtered by type
     *
     * @param accountId       - the target account
     * @param transactionType - the target type of the transactions
     * @param limit           - the limit how many transactions we should provide
     * @return                - a list with all transactions
     */
    @GET("transaction/account/type/recent")
    Observable<List<Transaction>> getRecentTransactionsOfAccountOfType(@Query("accountId") int accountId,
                                                                           @Query("limit") int limit,
                                                                           @Query("transactionType") TransactionType transactionType);

}
