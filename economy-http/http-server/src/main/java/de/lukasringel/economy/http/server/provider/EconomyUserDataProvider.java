package de.lukasringel.economy.http.server.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.lukasringel.economy.api.model.HighLoad;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.provider.EconomyUserProvider;
import de.lukasringel.economy.http.server.datasource.implementation.EconomyUserDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class EconomyUserDataProvider implements DataProvider, EconomyUserProvider {

    private final EconomyUserDataSource userDataSource;

    private final LoadingCache<Integer, EconomyUser> userLoadingCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public EconomyUser load(Integer userId) {
                    return userDataSource.getUserById(userId);
                }
            });

    /**
     * This method gets called:
     * 1. if the 'refresh' console command was executed
     * 2. if the application bootstraps
     */
    @Override
    public void refresh() {
        userLoadingCache.invalidateAll();
    }

    /**
     * This method creates a new user
     *
     * @param externalIdentifiers - related external identifiers (Format: key, value, key, value...)
     * @return - the new created account
     */
    @Override
    public EconomyUser createUser(String... externalIdentifiers) {
        return userDataSource.createUser(externalIdentifiers);
    }

    /**
     * This method searches for a user with the provided id
     *
     * @param id - the id of the user
     * @return - an optional of the user since we don't know if a user with this id exists
     */
    @Override
    public Optional<EconomyUser> getUserById(int id) {
        return Optional.of(userLoadingCache.getUnchecked(id));
    }

    /**
     * This method searches for a user with the provided identifier
     *
     * @param key   - the key of the identifier
     * @param query - the query of the identifier
     * @return - an optional of the user since we don't know if there will be a match
     */
    @Override
    public Optional<EconomyUser> getUserByIdentifier(String key, String query) {
        return Optional.of(userDataSource.getUserByExternalIdentifier(key, query));
    }

    /**
     * This method searches for all suspended users
     *
     * @return - a list of all suspended users
     */
    @Override
    @HighLoad
    public List<EconomyUser> getSuspendedUsers() {
        return userDataSource.getAllSuspendedUsers();
    }

    /**
     * This method searches for all users which were created before the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return - a list with all found users
     */
    @Override
    @HighLoad
    public List<EconomyUser> getUsersCreatedBeforeTimeStamp(long timestamp) {
        return userDataSource.getUsersCreatedBeforeTimeStamp(timestamp);
    }

    /**
     * This method searches for all users which were created after the provided timestamp
     *
     * @param timestamp - the timestamp
     * @return - a list with all found users
     */
    @Override
    @HighLoad
    public List<EconomyUser> getUsersCreatedAfterTimeStamp(long timestamp) {
        return userDataSource.getUsersCreatedAfterTimeStamp(timestamp);
    }

}
