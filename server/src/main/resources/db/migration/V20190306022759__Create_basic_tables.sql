create table currencies
(
    ID        serial       not null primary key,
    CODE      varchar(20)  not null unique,
    NAME      varchar(100) not null unique,
    UNICODE   varchar(10)  not null,
    COUNTRY   varchar(100),
    IS_CRYPTO boolean
);

create table users
(
    ID       serial        not null primary key,
    LOGIN    varchar(100)  not null unique,
    NAME     varchar(100),
    PASSWORD varchar(1000) not null,
    SALT     varchar(1000) not null
);

create table sessions
(
    id               bigserial                not null primary key,
    user_id          int                      not null references users (ID),
    created_at       timestamp with time zone not null,
    last_activity_at timestamp with time zone not null
);

create table accounts
(
    ID          bigserial                not null primary key,
    OWNER_ID    int                      not null references users (ID),
    CURRENCY_ID int                      not null references currencies (ID),
    NAME        varchar(100)             not null,
    ACC_TYPE    smallint                 not null,
    CREATED_AT  timestamp with time zone not null
);

create table transactions
(
    ID              bigserial                not null primary key,
    actor           int                      not null references users (ID),
    at              timestamp with time zone not null,
    source          bigint references accounts (ID),
    destination     bigint references accounts (ID),
    conversion_rate decimal                  not null,
    amount          decimal                  not null
);

create unique index currencies_idx
    on currencies (ID);
create unique index currencies_code_x
    on currencies (CODE);
create unique index currencies_name_x
    on currencies (NAME);

create unique index users_idx
    on users (ID);
create unique index users_login_x
    on users (LOGIN);

create unique index sessions_idx
    on sessions (id);
create unique index sessions_users_idx
    on sessions (user_id);

create unique index accounts_idx
    on accounts (ID);

create unique index transactions_idx
    on transactions (ID);
create index transactions_at_x
    on transactions (at);


-- Base data
INSERT INTO currencies(ID, CODE, NAME, UNICODE, COUNTRY, IS_CRYPTO)
VALUES (1, 'rur', 'Russian Ruble', '\u20BD', 'Russian Federation', false),
       (2, 'usd', 'United States Dollar', '$', 'United States of American', false),
       (3, 'eur', 'Euro', '€', 'Europe', false),
       (4, 'btc', 'Bitcoin', 'btc', null, true),
       (5, 'eth', 'Etherium', 'eth', null, true);

INSERT INTO Users(ID, LOGIN, NAME, PASSWORD, SALT)
VALUES (1, 'admin', 'admin', '123', '');


-- Temporary data for testing purposes
INSERT INTO Users(ID, LOGIN, NAME, PASSWORD, SALT)
VALUES (2, 'vasya', 'Vasya', '123', ''),
       (3, 'petya', 'Petya', '123', '');

INSERT into accounts(OWNER_ID, CURRENCY_ID, NAME, ACC_TYPE, CREATED_AT)
VALUES (2, 1, 'Bank1 card', 1, CURRENT_TIME),
       (2, 2, 'Bank2 card', 3, CURRENT_TIME),
       (2, 4, 'Secret wallet', 4, CURRENT_TIME),
       (3, 1, 'Cabal', 2, CURRENT_TIME),
       (3, 1, 'Pocket money', 3, CURRENT_TIME);