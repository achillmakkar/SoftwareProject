/*==============================================================*/
--Codestart_Achill_25.03.2024_OccupancyTable
DROP TABLE IF EXISTS occupancy;
DROP TABLE IF EXISTS hotel;
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
