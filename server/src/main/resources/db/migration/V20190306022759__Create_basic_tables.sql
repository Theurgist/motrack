create table currencies (
  CODE varchar(20) not null,
  NAME varchar(100) not null,
  UNICODE varchar(10) not null,
  COUNTRY varchar(100),
  IS_CRYPTO boolean
);

--      Currency("rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
--      Currency("usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
--      Currency("eur", "Euro", "€", Option("Europe"), isCrypto = false),
-- INSERT INTO Circle (radius) VALUES (?)