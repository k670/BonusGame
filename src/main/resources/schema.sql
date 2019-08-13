
create table if not exists UserModel
(
    id    INTEGER not null AUTO_INCREMENT PRIMARY KEY ,
    coins INTEGER not null
);

create table if not exists BonusModel
(
    id    INTEGER not null AUTO_INCREMENT PRIMARY KEY ,
    value INTEGER not null,
    chance INTEGER not null
);