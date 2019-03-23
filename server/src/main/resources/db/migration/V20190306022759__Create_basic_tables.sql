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
    ID    serial       not null primary key,
    LOGIN varchar(100) not null unique,
    NAME  varchar(100)
);
create table accounts
(
    ID       bigserial not null primary key,
    OWNER_ID int references users (ID)
);

create unique index currencies_idx
    on currencies (ID);
create unique index currencies_codex
    on currencies (CODE);

create unique index users_idx
    on users (ID);
create unique index users_loginx
    on users (LOGIN);

create unique index accounts_idx
    on accounts (ID);


--      Currency("rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
--      Currency("usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
--      Currency("eur", "Euro", "€", Option("Europe"), isCrypto = false),
-- INSERT INTO Circle (radius) VALUES (?)