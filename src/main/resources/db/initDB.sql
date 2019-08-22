DROP TABLE IF EXISTS payments;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE payments
(
  id               UUID  PRIMARY KEY NOT NULL DEFAULT uuid_generate_v1(),
  id_sender        INTEGER                 NOT NULL,
  id_recipient     INTEGER                 NOT NULL,
  amount           NUMERIC                 NOT NULL
);