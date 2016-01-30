CREATE TABLE IF NOT EXISTS asteroid_type
(
 id int,
 name String,
 image String, 
 imageWidth int,
 imageHeight int,
 type String
); 

CREATE TABLE IF NOT EXISTS object
(
 image string,
 Id int not null
);

CREATE TABLE IF NOT EXISTS level_object
(
 level_number int,
 scale real,
 objectId int
);

CREATE TABLE IF NOT EXISTS level
(
number int,
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
 image String,
 imageWidth int,
 imageHeight int
);

CREATE TABLE IF NOT EXISTS cannon(
 attachPoint String,
 emitPoint String,
 image String,
 imageWidth int,
 imageHeight int,
 attackImage String,
 attackImageWidth int,
 attackImageHeight int,
 attackSound String,
 damage int
);

CREATE TABLE IF NOT EXISTS extra_part
(
 attachPoint String,
 image String,
 imageWidth int
 imageHeight int
);

CREATE TABLE IF NOT EXISTS engine
(
 baseSpeed int,
 baseTurnRate int,
 attachPoint String,
 image String,
 imageWidth int,
 imageHeight int
);

CREATE TABLE IF NOT EXISTS power_core
(
cannonBoost int,
engineBoost int,
image String
);
