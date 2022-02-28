CREATE TABLE `notification_api_retry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint(20) NOT NULL ,
  `payload_json` text NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;