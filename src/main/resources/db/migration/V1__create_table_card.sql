CREATE TABLE IF NOT EXISTS card (id UUID NOT NULL PRIMARY KEY,
    "name" varchar(200),
    released_at TIMESTAMP,
    cmc DECIMAL(10,2),
    type_line VARCHAR(4000),
    mana_cost VARCHAR(4000),
    oracle_text VARCHAR(4000),
    color_identity VARCHAR(10)[],
    colors VARCHAR(10)[],
    produced_mana VARCHAR(10)[]
)