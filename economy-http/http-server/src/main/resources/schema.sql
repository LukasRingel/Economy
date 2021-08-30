create table economy_economies (
    id                 int auto_increment primary key,
    name               varchar(100)       not null,
    startValue         double default 0   not null,
    increaseMultiplier double default 1   not null,
    decreaseMultiplier double default 1   not null,
    constraint economy_economies_name_uindex
        unique (name)
);

create table economy_transactions (
    id        int auto_increment primary key,
    accountId int                not null,
    amount    double             not null,
    timestamp mediumtext         not null,
    comment   text               null,
    type      tinyint            not null
);

create index economy_transactions_accountId_index
    on economy_transactions (accountId);

create table economy_users (
    id        int auto_increment primary key,
    suspended bit default b'0'   not null,
    createdAt mediumtext         not null
);

create table economy_users_accounts (
    id        int auto_increment primary key,
    userId    int                not null,
    economyId int                not null,
    amount    double             not null,
    constraint economy_users_accounts_userId_economyId_uindex
        unique (userId, economyId)
);

create table economy_users_identifiers (
    id         int auto_increment primary key,
    userId     int                not null,
    identifier varchar(100)       not null,
    value      varchar(100)       not null,
    active     bit default b'1'   not null,
    createdAt  mediumtext         not null,
    constraint economy_users_identifiers_userId_identifier_uindex
        unique (userId, identifier)
);



