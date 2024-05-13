/*==============================================================*/
--Codestart_Achill_25.03.2024_OccupancyTable
DROP TABLE IF EXISTS occupancy;
DROP TABLE IF EXISTS hotel;
DROP TABLE IF EXISTS import_events;

--Codeende_Achill_25.03.2024_OccupancyTable
/*==============================================================*/
/* Table: hotel                                                 */
/*==============================================================*/
CREATE TABLE hotel
(
  id      INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
  category int NOT NULL ,
  name    TEXT NOT NULL,
  owner   TEXT NOT NULL,
  contact TEXT NOT NULL,
  address TEXT NOT NULL,
  city    TEXT NOT NULL,
  zip     TEXT NOT NULL,
  phone   TEXT NOT NULL,
  rooms   int  not null,
  beds    int  not null,

--Codestart_#10_Dominik_30.03.2024_Add attributes (URL, family-friendly, dog-friendly, Spa, Fitness) to master data
  url                TEXT     DEFAULT 'https://www.fh-vie.ac.at/',
  family_friendly    BOOLEAN  DEFAULT FALSE,
  dog_friendly       BOOLEAN  DEFAULT FALSE,
  spa                BOOLEAN  DEFAULT FALSE,
  fitness            BOOLEAN  DEFAULT FALSE,
--Codeend_#10_Dominik_30.03.2024_Add attributes (URL, family-friendly, dog-friendly, Spa, Fitness) to master data

--Codestart_Dominik_30.03.2024_Added attributes e-mail_address and subscribed
  email_address      TEXT     DEFAULT 'hotelexample01@gmail.com',
  subscribed         BOOLEAN  DEFAULT FALSE
--Codeend_Dominik_30.03.2024_Added attributes e-mail_address and subscribed

);

--Codestart_Achill_25.03.2024_OccupancyTable
CREATE TABLE occupancy
(
    occupancy_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id INT,
    rooms INT NOT NULL,
    usedrooms INT NOT NULL,
    beds INT NOT NULL,
    usedbeds INT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    nationality int NULL,
    CONSTRAINT fk_hotel FOREIGN KEY (id) REFERENCES hotel(id)
);
--Codeende_Achill_25.03.2024_OccupancyTable

-- Codestart_Achill_02.05.2024_ImportEventsTable
DROP TABLE IF EXISTS import_events;

CREATE TABLE import_events
(
    event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME NOT NULL,
    status VARCHAR(255) NOT NULL,
    user VARCHAR(255) NOT NULL
);
-- Codeende_Achill_02.05.2024_ImportEventsTable

-- Codeanfang Veton 13.05.2024_CreatingRoles
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users (
    user_id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(45) NOT NULL,
    full_name varchar(45) NOT NULL,
    password varchar(64) NOT NULL,
    enabled tinyint(4) DEFAULT 1  -- Standardwert als Beispiel gesetzt, anpassen nach Bedarf
);

CREATE TABLE roles (
    role_id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(45) NOT NULL
);

CREATE TABLE users_roles (
    user_id int(11) NOT NULL,
    role_id int(11) NOT NULL,
    PRIMARY KEY(user_id, role_id),  -- Composite Primary Key für die Verknüpfungstabellen
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
);
