CREATE TABLE room (
    id INTEGER PRIMARY KEY,
    name VARCHAR DEFAULT 'Casino',
    password VARCHAR
);

CREATE TABLE player (
    id INTEGER PRIMARY KEY,
    name VARCHAR DEFAULT 'Player',
    photo VARCHAR DEFAULT 'https://upload.wikimedia.org/wikipedia/commons/9/98/OOjs_UI_icon_userAvatar.svg',
    points SMALLINT DEFAULT 1,
);

CREATE TABLE vote (
    room INTEGER,
    player INTEGER,
    value SMALLINT DEFAULT 1,
    CONSTRAINT fk_room FOREIGN KEY(room) REFERENCES room(id),
    CONSTRAINT fk_player FOREIGN KEY(player) REFERENCES player(id)
);


