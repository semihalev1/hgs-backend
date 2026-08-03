INSERT INTO vehicle_classes (code, name)
VALUES ('CLASS_1', '1. Sınıf Araç'),
       ('CLASS_2', '2. Sınıf Araç'),
       ('CLASS_3', '3. Sınıf Araç'),
       ('CLASS_4', '4. Sınıf Araç'),
       ('CLASS_5', '5. Sınıf Araç')
ON CONFLICT (code) DO UPDATE
SET name = EXCLUDED.name;

WITH tariff_seed (gate_code, vehicle_class_code, fee) AS (
    VALUES
        ('CAMLICA', 'CLASS_1', 25.00),
        ('CAMLICA', 'CLASS_2', 35.00),
        ('CAMLICA', 'CLASS_3', 65.00),
        ('CAMLICA', 'CLASS_4', 85.00),
        ('CAMLICA', 'CLASS_5', 110.00),

        ('FSM', 'CLASS_1', 45.00),
        ('FSM', 'CLASS_2', 60.00),
        ('FSM', 'CLASS_3', 120.00),
        ('FSM', 'CLASS_4', 150.00),
        ('FSM', 'CLASS_5', 200.00),

        ('15_TEMMUZ', 'CLASS_1', 45.00),
        ('15_TEMMUZ', 'CLASS_2', 60.00),
        ('15_TEMMUZ', 'CLASS_3', 120.00),
        ('15_TEMMUZ', 'CLASS_4', 150.00),
        ('15_TEMMUZ', 'CLASS_5', 200.00),

        ('YSS', 'CLASS_1', 80.00),
        ('YSS', 'CLASS_2', 105.00),
        ('YSS', 'CLASS_3', 195.00),
        ('YSS', 'CLASS_4', 245.00),
        ('YSS', 'CLASS_5', 320.00),

        ('OSMANGAZI', 'CLASS_1', 800.00),
        ('OSMANGAZI', 'CLASS_2', 1280.00),
        ('OSMANGAZI', 'CLASS_3', 1520.00),
        ('OSMANGAZI', 'CLASS_4', 2020.00),
        ('OSMANGAZI', 'CLASS_5', 2550.00),

        ('AVRASYA', 'CLASS_1', 225.00),
        ('AVRASYA', 'CLASS_2', 340.00),
        ('AVRASYA', 'CLASS_3', 450.00),
        ('AVRASYA', 'CLASS_4', 565.00),
        ('AVRASYA', 'CLASS_5', 675.00)
)
INSERT INTO tariffs (gate_id, vehicle_class_id, fee)
SELECT g.id,
       vc.id,
       ts.fee
FROM tariff_seed ts
JOIN gates g
  ON g.code = ts.gate_code
JOIN vehicle_classes vc
  ON vc.code = ts.vehicle_class_code
ON CONFLICT (gate_id, vehicle_class_id) DO UPDATE
SET fee = EXCLUDED.fee;
