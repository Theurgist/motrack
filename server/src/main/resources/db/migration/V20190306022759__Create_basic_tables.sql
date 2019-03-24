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
create table accounts
(
    ID          bigserial    not null primary key,
    OWNER_ID    int          not null references users (ID),
    CURRENCY_ID int          not null references currencies (ID),
    NAME        varchar(100) not null,
    ACC_TYPE    smallint     not null,
    CREATED_AT  timestamp    not null
);

create table transactions
(
    ID              bigserial        not null primary key,
    actor           int              not null references users (ID),
    at              timestamp        not null,
    source          bigint references accounts (ID),
    destination     bigint references accounts (ID),
    conversion_rate double precision not null,
    amount          double precision not null
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

create unique index accounts_idx
    on accounts (ID);

create unique index transactions_idx
    on transactions (ID);
create index transactions_at_x
    on transactions (at);


--      Currency("rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
--      Currency("usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
--      Currency("eur", "Euro", "€", Option("Europe"), isCrypto = false),
-- INSERT INTO Circle (radius) VALUES (?)