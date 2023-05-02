-- drop table if exists relay_event;
create table IF NOT EXISTS relay_event (
	`id` 			INTEGER PRIMARY KEY AUTOINCREMENT,
	`event_id` 		char(64) DEFAULT "",
	`pubkey` 		char(64) DEFAULT "",
	`kind` 			INTEGER DEFAULT 0,
	`created_at`	INTEGER DEFAULT 0,
	`content` 		TEXT DEFAULT "",
	`sig` 			char(64) DEFAULT "",
	`create_time` 	INTEGER DEFAULT 0, 
    `tags` 			TEXT DEFAULT "",
    `delegator` 	CHAR(200) DEFAULT "",
    `deduplication` TEXT DEFAULT "",
    `first_seen` 	INTEGER DEFAULT 0,
    `deleted_at` 	INTEGER DEFAULT 0,
    `expires_at` 	INTEGER DEFAULT 0
);
	