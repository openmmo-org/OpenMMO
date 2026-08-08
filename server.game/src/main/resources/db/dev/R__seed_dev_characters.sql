-- Dev characters are built by DevCharacterSeeder now, so their start state comes from
-- NewGameStarts instead of being copied into SQL. This only clears the rows the old seed left.
DELETE FROM characters WHERE id IN (102400, 364544);

-- Gives the developer bit to characters made through the client, on the dev accounts only.
UPDATE characters SET permissions = permissions | 256 WHERE user_id IN (1, 2);
