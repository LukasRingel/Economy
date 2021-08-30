package de.lukasringel.economy.http.client;

import de.lukasringel.economy.http.client.request.EconomyAccountRequests;
import de.lukasringel.economy.http.client.request.EconomyRequests;
import de.lukasringel.economy.http.client.request.EconomyTransactionRequests;
import de.lukasringel.economy.http.client.request.EconomyUserRequests;
import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * This class represents our http client to get information from the http server / api
 */

@Getter
public class EconomyHttpClient {

    private final EconomyAccountRequests accountRequests;
    private final EconomyRequests economyRequests;
    private final EconomyTransactionRequests transactionRequests;
    private final EconomyUserRequests userRequests;


    /**
     * This constructor creates instances of retrofit and our request interfaces
     *
     * @param baseUrl - the base url of our api (https://api.example.com/)
     * @param apiKey  - the used api password / key
     */
    public EconomyHttpClient(String baseUrl, String apiKey) {

        Retrofit retrofit = createRetrofit(apiKey, baseUrl);

        this.accountRequests = retrofit.create(EconomyAccountRequests.class);
        this.economyRequests = retrofit.create(EconomyRequests.class);
        this.transactionRequests = retrofit.create(EconomyTransactionRequests.class);
        this.userRequests = retrofit.create(EconomyUserRequests.class);

    }

    /**
     * This method builds our retrofit client
     *
     * @param baseUrl - the base url of our api (https://api.example.com/)
     * @param apiKey  - the used api password / key
     * @return        - our instance of retrofit
     */
    private Retrofit createRetrofit(String apiKey, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.SECONDS)
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .addInterceptor(chain -> chain.proceed(chain.request().newBuilder().addHeader("X-API-KEY", apiKey).build()))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
