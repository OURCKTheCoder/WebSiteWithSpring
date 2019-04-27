CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `entity_type` tinyint(4) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_index` (`entity_id`,`entity_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin

CREATE TABLE `entity_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
