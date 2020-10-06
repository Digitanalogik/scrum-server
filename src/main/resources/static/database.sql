CREATE TABLE room (
    id INTEGER PRIMARY KEY,
    name VARCHAR DEFAULT 'Casino',
    password VARCHAR
);

CREATE TABLE player (
    id INTEGER PRIMARY KEY,
    name VARCHAR DEFAULT 'Player',
    photo VARCHAR DEFAULT '',
    room SMALLINT DEFAULT 0,
    vote SMALLINT DEFAULT 0,
);

-- CREATE TABLE vote (
--    room INTEGER,
--    player INTEGER,
--    value SMALLINT DEFAULT 1,
--    CONSTRAINT fk_room FOREIGN KEY(room) REFERENCES room(id),
--    CONSTRAINT fk_player FOREIGN KEY(player) REFERENCES player(id)
--);


