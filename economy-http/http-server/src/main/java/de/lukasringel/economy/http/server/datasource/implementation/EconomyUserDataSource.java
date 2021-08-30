package de.lukasringel.economy.http.server.datasource.implementation;

import de.lukasringel.economy.api.model.ExternalIdentifier;
import de.lukasringel.economy.api.model.economy.Economy;
import de.lukasringel.economy.api.model.economy.EconomyAccount;
import de.lukasringel.economy.api.model.economy.EconomyUser;
import de.lukasringel.economy.api.provider.EconomyProvider;
import de.lukasringel.economy.http.server.datasource.DataSource;
import de.lukasringel.economy.http.server.datasource.transformer.EconomyUserResultSetTransformer;
import de.lukasringel.economy.http.server.datasource.transformer.ExternalIdentifierResultSetTransformer;
import lombok.RequiredArgsConstructor;
import me.xkuyax.utils.mysql.MysqlConnection;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This data source provides data for our local EconomyUserProvider
 */

@Component
@RequiredArgsConstructor
public class EconomyUserDataSource implements DataSource {

    private static final EconomyUserResultSetTransformer USER_TRANSFORMER = new EconomyUserResultSetTransformer();
    private static final ExternalIdentifierResultSetTransformer EXTERNAL_IDENTIFIER_TRANSFORMER = new ExternalIdentifierResultSetTransformer();

    private final MysqlConnection mysqlConnection;
    private final EconomyProvider economyProvider;

    /**
     * This method searches for a user with the provided instance
     *
     * @param id - the id of the target user
     * @return - the user we found
     */
    public EconomyUser getUserById(int id) {
        List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults = mysqlConnection.queryList("select " +
                        "       user.id           as userId, " +
                        "       user.suspended    as userSuspended, " +
                        "       user.createdAt    as userCreatedAt, " +
                        "       account.id        as accountId, " +
                        "       account.economyId as accountEconomyId, " +
                        "       account.amount    as accountWorth " +
                        "from economy_users user " +
                        "         inner join economy_users_accounts account on account.userId = user.id " +
                        "where user.id = ?;",
                firstPositionIntegerFiller(id),
                USER_TRANSFORMER);

        return loadEconomyUserByTemporaryData(temporaryResults);
    }

    /**
     * This method searches for our user by the given external identifier.
     *
     * @param key   - the key of our identifier
     * @param value - the value we search for
     * @return - our EconomyUser / null if there aren't any results
     */
    public EconomyUser getUserByExternalIdentifier(String key, String value) {
        List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults = mysqlConnection.queryList("select " +
                        "       user.id           as userId, " +
                        "       user.suspended    as userSuspended, " +
                        "       user.createdAt    as userCreatedAt, " +
                        "       account.id        as accountId, " +
                        "       account.economyId as accountEconomyId, " +
                        "       account.amount    as accountWorth " +
                        "from economy_users_identifiers identifier " +
                        "         inner join economy_users user on user.id = identifier.id " +
                        "         inner join economy_users_accounts account on account.userId = user.id " +
                        "where identifier.identifier = ? " +
                        "  and identifier.value = ?;",
                flexStringFiller(key, value),
                USER_TRANSFORMER);

        return loadEconomyUserByTemporaryData(temporaryResults);
    }

    /**
     * This method construct an EconomyUser instance by temporary results
     *
     * @param temporaryResults - our database results
     * @return - our EconomyUser / null if there aren't any results
     */
    private EconomyUser loadEconomyUserByTemporaryData(List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults) {
        // if we receive 0 objects, there isn't any user any none accounts related with this id
        if (temporaryResults.size() == 0) {
            return null;
        }

        // we can get the first object here since we checked for that above
        EconomyUserResultSetTransformer.TemporaryResult firstResult = temporaryResults.get(0);
        int userId = firstResult.userId();

        // loading and mapping necessary stuff
        List<ExternalIdentifier> externalIdentifiers = getExternalIdentifiers(userId);
        List<EconomyAccount> accounts = temporaryResults.stream().map(temporaryResult -> {
            Economy economy = economyProvider.getEconomyById(temporaryResult.accountEconomyId()).orElse(null);

            // we filter for that later
            if (economy == null) return null;

            return new EconomyAccount(temporaryResult.accountId(), economy, temporaryResult.accountWorth());
        }).filter(Objects::nonNull).toList();

        return new EconomyUser(userId, externalIdentifiers, accounts, firstResult.userSuspended(), firstResult.userCreatedAt());
    }

    /**
     * This method loads all external Identifiers of a user from our database
     *
     * @param userId - the id of the target user
     * @return - a list with all external identifiers
     */
    public List<ExternalIdentifier> getExternalIdentifiers(int userId) {
        return mysqlConnection.queryList(
                "select * from economy_users_identifiers where userId = ? and active = 1;",
                firstPositionIntegerFiller(userId),
                EXTERNAL_IDENTIFIER_TRANSFORMER);
    }

    /**
     * This method load all users from our database which were created before the provided timestamp
     *
     * @param timestamp - our target time
     * @return          - a list with all matching users
     */
    public List<EconomyUser> getUsersCreatedBeforeTimeStamp(long timestamp) {
        List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults = mysqlConnection.queryList("select " +
                        "       user.id           as userId, " +
                        "       user.suspended    as userSuspended, " +
                        "       user.createdAt    as userCreatedAt, " +
                        "       account.id        as accountId, " +
                        "       account.economyId as accountEconomyId, " +
                        "       account.amount    as accountWorth " +
                        "from economy_users user " +
                        "         inner join economy_users_accounts account on account.userId = user.id " +
                        "where user.createdAt < ?;",
                firstPositionLongFiller(timestamp),
                USER_TRANSFORMER);

        List<EconomyUser> economyUsers = new ArrayList<>();
        temporaryResults.stream()
                .collect(Collectors.groupingBy(EconomyUserResultSetTransformer.TemporaryResult::userId))
                .forEach((userId, results) -> economyUsers.add(loadEconomyUserByTemporaryData(results)));

        return economyUsers;
    }

    /**
     * This method load all users from our database which were created after the provided timestamp
     *
     * @param timestamp - our target time
     * @return          - a list with all matching users
     */
    public List<EconomyUser> getUsersCreatedAfterTimeStamp(long timestamp) {
        List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults = mysqlConnection.queryList("select " +
                        "       user.id           as userId, " +
                        "       user.suspended    as userSuspended, " +
                        "       user.createdAt    as userCreatedAt, " +
                        "       account.id        as accountId, " +
                        "       account.economyId as accountEconomyId, " +
                        "       account.amount    as accountWorth " +
                        "from economy_users user " +
                        "         inner join economy_users_accounts account on account.userId = user.id " +
                        "where user.createdAt > ?;",
                firstPositionLongFiller(timestamp),
                USER_TRANSFORMER);

        List<EconomyUser> economyUsers = new ArrayList<>();
        temporaryResults.stream()
                .collect(Collectors.groupingBy(EconomyUserResultSetTransformer.TemporaryResult::userId))
                .forEach((userId, results) -> economyUsers.add(loadEconomyUserByTemporaryData(results)));

        return economyUsers;
    }

    /**
     * This method loads all suspended users from our database
     *
     * @return - a list with all matching users
     */
    public List<EconomyUser> getAllSuspendedUsers() {
        List<EconomyUserResultSetTransformer.TemporaryResult> temporaryResults = mysqlConnection.queryList("select " +
                        "       user.id           as userId, " +
                        "       user.suspended    as userSuspended, " +
                        "       user.createdAt    as userCreatedAt, " +
                        "       account.id        as accountId, " +
                        "       account.economyId as accountEconomyId, " +
                        "       account.amount    as accountWorth " +
                        "from economy_users user " +
                        "         inner join economy_users_accounts account on account.userId = user.id " +
                        "where user.suspended = 1;",
                emptyFiller(),
                USER_TRANSFORMER);

        List<EconomyUser> economyUsers = new ArrayList<>();
        temporaryResults.stream()
                .collect(Collectors.groupingBy(EconomyUserResultSetTransformer.TemporaryResult::userId))
                .forEach((userId, results) -> economyUsers.add(loadEconomyUserByTemporaryData(results)));

        return economyUsers;
    }

    /**
     * This method creates and saves a new user in our database
     *
     * @param externalIdentifiers - the external identifiers of our new user
     * @return - the new EconomyUser
     */
    public EconomyUser createUser(String... externalIdentifiers) {
        long createdAt = System.currentTimeMillis();

        int userId = mysqlConnection.keyInsert(
                "insert into economy_users(createdAt) values (?);",
                firstPositionLongFiller(createdAt)
        );

        List<ExternalIdentifier> identifiers = buildExternalIdentifiers(createdAt, userId, externalIdentifiers);

        return new EconomyUser(
                userId,
                identifiers,
                Collections.emptyList(),
                false,
                createdAt
        );
    }

    /**
     * This method creates and saves the external identifiers of our new account
     *
     * @param createdAt           - the creation date we use
     * @param userId              - the id of the target user
     * @param externalIdentifiers - the external identifier strings we want to work with
     * @return - a list with our external identifier instances
     */
    private List<ExternalIdentifier> buildExternalIdentifiers(long createdAt, int userId, String[] externalIdentifiers) {
        List<String> keys = IntStream
                .range(0, externalIdentifiers.length)
                .filter(index -> index % 2 == 0)
                .mapToObj(index -> externalIdentifiers[index])
                .toList();

        List<String> values = IntStream
                .range(0, externalIdentifiers.length)
                .filter(index -> index % 2 != 0)
                .mapToObj(index -> externalIdentifiers[index])
                .toList();

        if (keys.size() != values.size()) {
            throw new IndexOutOfBoundsException("Error while creating new User. Keys size (" + keys.size() + ") and " +
                    "values size (" + values.size() + ") does not match.");
        }

        List<ExternalIdentifier> identifiers = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {

            String key = keys.get(i);
            String value = values.get(i);

            int identifierId = mysqlConnection.keyInsert(
                    "insert into economy_users_identifiers(userId, identifier, value, createdAt) values (?,?,?,?);",
                    preparedStatement -> {
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setString(2, key);
                        preparedStatement.setString(3, value);
                        preparedStatement.setLong(4, createdAt);
                    }
            );

            identifiers.add(new ExternalIdentifier(identifierId, key, value, true, createdAt));

        }
        return identifiers;
    }

}
