-- Drops the two characters the SQL seed used to create, which DevCharacterSeeder now replaces.
DELETE FROM characters WHERE id IN (102400, 364544);

-- Gives the developer bit to characters made through the client, on the dev accounts only.
UPDATE characters SET permissions = permissions | 256 WHERE user_id IN (1, 2);
