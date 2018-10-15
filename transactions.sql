CREATE TABLE `transactions` (
  `ID` int(11) NOT NULL,
  `NameOnCard` varchar(256) NOT NULL,
  `CardNumber` varchar(45) NOT NULL,
  `CardType` varchar(45) NOT NULL,
  `ExpDate` varchar(16) NOT NULL,
  `UnitPrice` decimal(10,0) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `TotalPrice` decimal(10,0) NOT NULL,
  `CreatedOn` datetime NOT NULL,
  `CreatedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci