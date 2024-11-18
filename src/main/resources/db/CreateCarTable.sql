CREATE TABLE car
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    mark            VARCHAR(30),
    model           VARCHAR(30),
    nbcv            INT,
    year            DATE,
    weight          Double,
    length          Double,
    width           Double,
    height          Double,
    price           Double,
    box             VARCHAR(20),
    transmission    VARCHAR(20),
    energie         VARCHAR(20),
    rapport         INT,
    nbPortes        INT,
    nbPlaces        INT,
    cylinders       Double
);