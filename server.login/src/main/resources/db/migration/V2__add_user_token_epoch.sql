-- Raising a user's epoch invalidates every remember me token already issued to them.
ALTER TABLE users
  ADD COLUMN token_epoch INT NOT NULL DEFAULT 0;
