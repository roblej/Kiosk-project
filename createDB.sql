CREATE TABLE `admin` (
  `a_password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `coupons` (
  `c_idx` bigint NOT NULL AUTO_INCREMENT COMMENT '쿠폰 고유 ID',
  `c_code` varchar(50) DEFAULT NULL COMMENT '쿠폰 번호',
  `c_start` date DEFAULT (curdate()) COMMENT '쿠폰 발급일시',
  `c_end` date DEFAULT NULL COMMENT '쿠폰 유효 기간 만료일시',
  `c_discount_rate` int DEFAULT NULL COMMENT '쿠폰 할인율',
  `is_coupon_used` tinyint(1) NOT NULL DEFAULT '0' COMMENT '쿠폰 사용 여부',
  `u_id` varchar(45) DEFAULT NULL COMMENT 'users테이블의 u_id 외래키참조',
  PRIMARY KEY (`c_idx`),
  UNIQUE KEY `c_code` (`c_code`),
  KEY `fk_coupons_users_uid` (`u_id`),
  CONSTRAINT `fk_coupons_users_uid` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `coupons_chk_1` CHECK (((`c_discount_rate` >= 1) and (`c_discount_rate` <= 100)))
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_items` (
  `oi_idx` int NOT NULL AUTO_INCREMENT,
  `oi_id` varchar(50) NOT NULL,
  `product_code` varchar(50) NOT NULL,
  `oi_quantity` int NOT NULL,
  `oi_price` int DEFAULT NULL,
  `oi_size` varchar(20) DEFAULT NULL,
  `options` text,
  PRIMARY KEY (`oi_idx`),
  KEY `oi_id` (`oi_id`),
  KEY `product_code` (`product_code`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`oi_id`) REFERENCES `orders` (`o_number`) ON DELETE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_code`) REFERENCES `products` (`p_code`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
  `o_idx` int NOT NULL AUTO_INCREMENT,
  `o_number` varchar(50) DEFAULT NULL,
  `o_total_amount` int DEFAULT NULL,
  `o_status` enum('조리중','처리완료','취소') NOT NULL DEFAULT '조리중',
  `o_is_takeout` tinyint(1) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `o_coupon_sale` int DEFAULT NULL,
  PRIMARY KEY (`o_idx`),
  UNIQUE KEY `o_number` (`o_number`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`u_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=319 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `p_code` varchar(50) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_price` int NOT NULL,
  `p_category` varchar(50) DEFAULT NULL,
  `p_image_url` varchar(255) DEFAULT NULL,
  `p_stock` int DEFAULT NULL,
  PRIMARY KEY (`p_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `u_id` varchar(50) NOT NULL,
  `u_password` varchar(255) NOT NULL,
  `u_birth` date NOT NULL,
  `u_gender` enum('M','F') NOT NULL,
  `u_phone` varchar(100) DEFAULT NULL,
  `u_joinDate` date DEFAULT (curdate()),
  `u_order` int DEFAULT '0',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
