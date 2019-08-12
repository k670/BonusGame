/*


CREATE SCHEMA  IF NOT EXISTS users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  coins INT NOT NULL
);*/
create table UserModel
(
    id    INTEGER not null AUTO_INCREMENT PRIMARY KEY ,
    coins INTEGER not null
);

create table BonusModel
(
    id    INTEGER not null AUTO_INCREMENT PRIMARY KEY ,
    value INTEGER not null,
    chance INTEGER not null
);