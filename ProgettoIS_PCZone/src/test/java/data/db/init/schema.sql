
CREATE TABLE IF NOT EXISTS UTENTEREGISTRATO (
  `Nickname` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Foto` varchar(2048) DEFAULT NULL,
  `Descrizione` varchar(1500) NOT NULL,
  `isGestore` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`Nickname`)
);

CREATE TABLE IF NOT EXISTS WISHLIST (
  `IDWishlist` int NOT NULL AUTO_INCREMENT,
  `Nickname` varchar(50) NOT NULL,
  PRIMARY KEY (`IDWishlist`,`Nickname`),
  FOREIGN KEY (`Nickname`) REFERENCES `utenteregistrato` (`Nickname`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS COMPONENTE (
  `IDComponente` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(70) NOT NULL,
  `Prezzo` float NOT NULL,
  `Descrizione` varchar(750) NOT NULL,
  `DataUscita` date NOT NULL,
  `Immagine` varchar(2048) DEFAULT NULL,
  `Tipo` varchar(40) NOT NULL,
  `LinkAcquisto` varchar(2048) NOT NULL,
  PRIMARY KEY (`IDComponente`)
);


CREATE TABLE IF NOT EXISTS CONFIGURAZIONE (
  `IDOrdine` int NOT NULL AUTO_INCREMENT,
  `DataCompletamento` datetime NOT NULL,
  `Nickname` varchar(50) NOT NULL,
  PRIMARY KEY (`IDOrdine`),
  FOREIGN KEY (`Nickname`) REFERENCES `utenteregistrato` (`Nickname`)
);


CREATE TABLE IF NOT EXISTS CONTIENE (
  `IDWishlist` int NOT NULL,
  `Nickname` varchar(50) NOT NULL,
  `IDComponente` int NOT NULL,
  PRIMARY KEY (`IDWishlist`,`Nickname`,`IDComponente`),
  FOREIGN KEY (`IDWishlist`) REFERENCES `wishlist` (`IDWishlist`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`Nickname`) REFERENCES `wishlist` (`Nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS GENERE (
  `Nome` varchar(40) NOT NULL,
  PRIMARY KEY (`Nome`)
);


CREATE TABLE IF NOT EXISTS GUIDA (
  `IDGuida` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(50) NOT NULL,
  `Data` date NOT NULL,
  `Articolo` varchar(1500) NOT NULL,
  PRIMARY KEY (`IDGuida`)
);


CREATE TABLE IF NOT EXISTS INCLUSO (
  `IDOrdine` int NOT NULL,
  `IDComponente` int NOT NULL,
  PRIMARY KEY (`IDOrdine`,`IDComponente`),
  FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`),
  FOREIGN KEY (`IDOrdine`) REFERENCES `configurazione` (`IDOrdine`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS RECENSIONE (
  `Titolo` varchar(50) NOT NULL,
  `Punteggio` int NOT NULL,
  `Testo` varchar(250) NOT NULL,
  `DataCreazione` date NOT NULL,
  `IDComponente` int NOT NULL,
  `Nickname` varchar(50) NOT NULL,
  PRIMARY KEY (`Titolo`,`IDComponente`,`Nickname`),
  FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`Nickname`) REFERENCES `utenteregistrato` (`Nickname`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS SUGGERISCE (
  `IDGuida` int NOT NULL,
  `IDComponente` int NOT NULL,
  PRIMARY KEY (`IDGuida`,`IDComponente`),
  FOREIGN KEY (`IDGuida`) REFERENCES `guida` (`IDGuida`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE
);


