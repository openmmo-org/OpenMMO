-- Dev accounts admin/admin and test/test. The hashes are the SHA-1 the client sends.
-- Only applied when db.seedDev is enabled, never in production.
INSERT INTO users (username, display_name, password_hash)
VALUES
  ('admin', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
  ('test', 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3')
ON CONFLICT (username) DO NOTHING;
