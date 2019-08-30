
use launcher;

CREATE TABLE Softwares (
    softwareId int NOT NULL auto_increment,
    `name` varchar(50) NOT NULL,
    isAGame boolean not null,
    pathExec varchar(200) not null,
    args varchar(100) not null,
    
	PRIMARY KEY (softwareId)
);

CREATE TABLE Infos (
	softwareId int NOT NULL,
    
    `description` Text,
	imgUrl varchar(200),
    releaseYear int NULL,
    PRIMARY KEY (softwareId),
	FOREIGN KEY (softwareId) REFERENCES Softwares (softwareId)
);

CREATE TABLE Categories (
    categoryId int NOT NULL auto_increment,
    isForAGame boolean,
    name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (categoryId)
);

CREATE TABLE `categories-softwares` (
    softwareId int NOT NULL,
    categoryId int NOT NULL,
    
    PRIMARY KEY ( softwareId,categoryId),
    
     FOREIGN KEY(softwareId) REFERENCES Softwares (softwareId),
     FOREIGN KEY(categoryId) REFERENCES Categories (categoryId)
);


