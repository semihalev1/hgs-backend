ALTER TABLE transactions
    ADD COLUMN IF NOT EXISTS vehicle_class_id BIGINT;

UPDATE transactions t
SET vehicle_class_id = v.vehicle_class_id
FROM vehicles v
WHERE t.vehicle_class_id IS NULL
  AND t.vehicle_id = v.id;

ALTER TABLE transactions
    ALTER COLUMN vehicle_class_id SET NOT NULL;

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'fk_transactions_vehicle_class'
          AND conrelid = 'transactions'::regclass
    ) THEN
        ALTER TABLE transactions
            ADD CONSTRAINT fk_transactions_vehicle_class
                FOREIGN KEY (vehicle_class_id) REFERENCES vehicle_classes (id);
    END IF;
END
$$;

CREATE INDEX IF NOT EXISTS ix_transactions_vehicle_class_id
    ON transactions (vehicle_class_id);
