-- Align priority column with enum storage (string) and ensure default
ALTER TABLE tickets ALTER COLUMN priority VARCHAR(20);
ALTER TABLE tickets ALTER COLUMN priority SET NOT NULL;
ALTER TABLE tickets ALTER COLUMN priority SET DEFAULT 'MEDIUM';
