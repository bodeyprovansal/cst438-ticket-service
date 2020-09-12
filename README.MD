# Ticket Service

This repo houses the ticket service for the CST 438 ticket system.

## Setup
- Create a MySQL DB, listening on port `33307` with the username of `root` and a password of `testing`
- Clone this repo into Eclipse
- Install Maven
- Run the MySQL seed for this service
```sql
USE `ticket_sys_db`;

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
    `id` int NOT NULL AUTO_INCREMENT,
    `created_at` text NOT NULL,
    `submitted_user_id` int NOT NULL,
    `ticket_status` enum('Awaiting Administrator', 'Awaiting User', 'Closed', 'Open', 'Resolved'),
    `ticket_subject` varchar(50) NOT NULL,
    `initial_message` varchar(500) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `submitted_user_id` (`submitted_user_id`),
    CONSTRAINT `submitted_fk` FOREIGN KEY (`submitted_user_id`) REFERENCES `users` (`id`)
);
​
DROP TABLE IF EXISTS `ticket_messages`;
CREATE TABLE `ticket_messages` (
    `id` int NOT NULL AUTO_INCREMENT,
    `created_at` datetime NOT NULL,
    `ticket_id` int NOT NULL,
    `user_id` int NOT NULL,
    `message` varchar(500) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `ticket_id` (`ticket_id`),
    CONSTRAINT `ticket_fk` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `commenter_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
```
- Run the application and you can access the API with the root endpoint of [localhost](http://localhost:8002/)
