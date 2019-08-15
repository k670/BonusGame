create table if not exists UserModel
(
    id    INTEGER not null AUTO_INCREMENT,
    coins DOUBLE  not null
);

create table if not exists BonusModel
(
    id     INTEGER     not null AUTO_INCREMENT,
    name   VARCHAR(50) not null,
    value  DOUBLE      not null,
    chance DOUBLE      not null
);