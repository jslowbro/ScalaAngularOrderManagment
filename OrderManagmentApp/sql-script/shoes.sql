DROP database if exists `shoesdb`;
CREATE database `shoesdb`;
USE  `shoesdb`;

DROP TABLE if exists `items`;
DROP TABLE if exists `orders`;
DROP TABLE if exists `stock`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `items` (
  `name` nvarchar(70) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  KEY `FK_FROM_idx` (`order_id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
 ) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
 
 CREATE TABLE `stock` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `size` varchar(20) DEFAULT NULL,
    `color` varchar(20) DEFAULT NULL,
    `quantity` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
insert into `orders`(timestamp) values
	(NOW()),
    (NOW()),
    (NOW()),
    (NOW()),
    (NOW());
insert into `items`(name, age , size , color ,order_id) values
	('Tommy', 20, 'S', 'RED', 1),
    ('Tommy', 30, 'M', 'RED', 2),
    ('Jade', 40, 'XL', 'RED', 3),
    ('Jade', 60, 'L', 'RED', 4),
    ('Jade', 70, 'S', 'RED', 4),
    ('Curtis', 23, 'M', 'RED', 4),
    ('Jade', 50, 'M', 'RED', 2),
    ('Curtis', 40, 'S', 'RED', 3),
    ('Curtis', 25, 'S', 'RED', 3),
    ('Tommy', 67, 'S', 'RED', 5);
    
    
insert into `stock`(size,color,quantity) values
	('S','BLUE',5),
    ('M','BLUE',5),
    ('L','BLUE',5),
    ('XL','BLUE',5),
    ('S','RED',5),
    ('M','RED',5),
    ('L','RED',5),
    ('XL','RED',5),
    ('S','GREEN',5),
    ('M','GREEN',5),
    ('L','GREEN',5),
    ('XL','GREEN',5);
    
