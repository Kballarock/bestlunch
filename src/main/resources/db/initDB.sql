CREATE SCHEMA IF NOT EXISTS bestlunch CHARACTER SET utf8;
DROP TABLE IF EXISTS user_roles, users, restaurant, menu, votes;

CREATE TABLE users
(
  id         INTEGER AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100)            NOT NULL,
  email      VARCHAR(64)             NOT NULL,
  password   VARCHAR(60)             NOT NULL,
  registered TIMESTAMP DEFAULT now() NOT NULL,
  enabled    BOOL      DEFAULT TRUE  NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 100000;

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(16),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE restaurant
(
  id          INTEGER AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(150)           NOT NULL,
  description VARCHAR(255)           NOT NULL,
  address     VARCHAR(150)           NOT NULL,
  added       DATETIME DEFAULT now() NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 100000;

CREATE TABLE menu
(
  id            INTEGER AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(255)                                  NOT NULL,
  price         DOUBLE(5, 2)                                  NOT NULL,
  menu_date     DATE DEFAULT (CURRENT_DATE + INTERVAL 1 YEAR) NOT NULL,
  restaurant_id INTEGER                                       NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 100000;

CREATE TABLE votes
(
  id            INTEGER AUTO_INCREMENT PRIMARY KEY,
  user_id       INTEGER                                       NOT NULL,
  restaurant_id INTEGER                                       NOT NULL,
  voting_date   DATE DEFAULT (CURRENT_DATE + INTERVAL 1 YEAR) NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE UNIQUE INDEX vote_unique_user_per_date_idx ON votes (user_id, voting_date);