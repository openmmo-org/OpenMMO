CREATE TABLE users (
  id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  -- Always lowercase. Lookups go through this column.
  username      VARCHAR(32) NOT NULL,
  display_name  VARCHAR(32) NOT NULL,
  -- Unsalted SHA-1 hex. The client already hashes the password before sending it.
  password_hash VARCHAR(40) NOT NULL,
  created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_users_username UNIQUE (username)
);
