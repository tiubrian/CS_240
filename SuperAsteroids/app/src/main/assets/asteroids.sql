CREATE TABLE IF NOT EXISTS asteroid_type
(
 id integer not null primary key autoincrement,
 name String,
 image text, 
 imageWidth int,
 imageHeight int,
 type String
); 

CREATE TABLE IF NOT EXISTS background_object
(
 image string,
 Id integer not null primary key autoincrement
);

CREATE TABLE IF NOT EXISTS level_object
(
 level_number int ,
 scale real,
 objectId int,
 position string
);

CREATE TABLE IF NOT EXISTS level
(
number integer not null primary key autoincrement,
title String,
hint String,
width int,
height int,
music String
);

CREATE TABLE IF NOT EXISTS level_asteroid
(
 levelnumber int,
 asteroidId int
);

CREATE TABLE IF NOT EXISTS main_body
(
 cannonAttach String,
 engineAttach String,
 extraAttach String,
 image text,
 imageWidth int,
 imageHeight int
);

CREATE TABLE IF NOT EXISTS cannon
(
 attachPoint String,
 emitPoint String,
 image text,
 imageWidth int,
 imageHeight int,
 attackImage text,
 attackImageWidth int,
 attackImageHeight int,
 attackSound String,
 damage int
);

CREATE TABLE IF NOT EXISTS extra_part
(
 attachPoint String,
 image text,
 imageWidth int
 imageHeight int
);

CREATE TABLE IF NOT EXISTS engine
(
 baseSpeed int,
 baseTurnRate int,
 attachPoint String,
 image text,
 imageWidth int,
 imageHeight int
);

CREATE TABLE IF NOT EXISTS power_core
(
cannonBoost int,
engineBoost int,
image text
);
