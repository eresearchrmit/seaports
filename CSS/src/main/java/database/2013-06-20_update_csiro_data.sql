/*
-- Removal of the 'picture' column in the CSIRO Data
-- Date: 2013-06-20 10:21
*/
ALTER TABLE `seaports`.`csiro_data` DROP COLUMN `picture`;

/*
-- Update of the climate parameters: swap of "Hotter & Drier" and "Most Likely" climate model in Region SSWFW
-- Date: 2013-06-20 10:21
*/
UPDATE `seaports`.`climate_params` SET `climate_model_id`='1' WHERE `id`='13';
UPDATE `seaports`.`climate_params` SET `climate_model_id`='1' WHERE `id`='14';
UPDATE `seaports`.`climate_params` SET `climate_model_id`='4' WHERE `id`='15';
UPDATE `seaports`.`climate_params` SET `climate_model_id`='4' WHERE `id`='16';

/*
-- Update of the CSIRO Data: swap of "Hotter & Drier" and "Most Likely" climate model in Region SSWFW
-- Date: 2013-06-20 10:21
*/
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (145,'2008-07-28 00:00:00',1.1,2030,13,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (146,'2008-07-28 00:00:00',-6.2,2030,13,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (147,'2008-07-28 00:00:00',-1.5,2030,13,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (148,'2008-07-28 00:00:00',-1.5,2030,13,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (149,'2008-07-28 00:00:00',2.1,2055,13,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (150,'2008-07-28 00:00:00',-11.6,2055,13,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (151,'2008-07-28 00:00:00',-2.8,2055,13,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (152,'2008-07-28 00:00:00',-2.9,2055,13,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (153,'2008-07-28 00:00:00',2.6,2070,13,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (154,'2008-07-28 00:00:00',-14.7,2070,13,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (155,'2008-07-28 00:00:00',-3.6,2070,13,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (156,'2008-07-28 00:00:00',-3.7,2070,13,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (157,'2008-07-28 00:00:00',1.1,2030,14,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (158,'2008-07-28 00:00:00',-6,2030,14,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (159,'2008-07-28 00:00:00',-1.5,2030,14,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (160,'2008-07-28 00:00:00',-1.5,2030,14,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (161,'2008-07-28 00:00:00',2.6,2055,14,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (162,'2008-07-28 00:00:00',-14.5,2055,14,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (163,'2008-07-28 00:00:00',-3.5,2055,14,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (164,'2008-07-28 00:00:00',-3.6,2055,14,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (165,'2008-07-28 00:00:00',3.6,2070,14,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (166,'2008-07-28 00:00:00',-20.1,2070,14,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (167,'2008-07-28 00:00:00',-4.9,2070,14,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (168,'2008-07-28 00:00:00',-5,2070,14,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (169,'2008-07-28 00:00:00',0.7,2030,15,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (170,'2008-07-28 00:00:00',-6.7,2030,15,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (171,'2008-07-28 00:00:00',1.1,2030,15,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (172,'2008-07-28 00:00:00',-0.9,2030,15,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (173,'2008-07-28 00:00:00',1.2,2055,15,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (174,'2008-07-28 00:00:00',-12.6,2055,15,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (175,'2008-07-28 00:00:00',2.1,2055,15,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (176,'2008-07-28 00:00:00',-1.8,2055,15,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (177,'2008-07-28 00:00:00',2.6,2070,15,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (178,'2008-07-28 00:00:00',-16,2070,15,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (179,'2008-07-28 00:00:00',2.6,2070,15,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (180,'2008-07-28 00:00:00',-2.3,2070,15,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (181,'2008-07-28 00:00:00',0.6,2030,16,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (182,'2008-07-28 00:00:00',-6.5,2030,16,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (183,'2008-07-28 00:00:00',1.1,2030,16,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (184,'2008-07-28 00:00:00',-0.9,2030,16,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (185,'2008-07-28 00:00:00',1.5,2055,16,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (186,'2008-07-28 00:00:00',-15.7,2055,16,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (187,'2008-07-28 00:00:00',2.6,2055,16,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (188,'2008-07-28 00:00:00',-2.2,2055,16,4);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (189,'2008-07-28 00:00:00',2.1,2070,16,1);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (190,'2008-07-28 00:00:00',-21.7,2070,16,3);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (191,'2008-07-28 00:00:00',3.6,2070,16,2);
INSERT INTO `csiro_data` (`id`,`creation_date`,`value`,`year`,`climate_params_id`,`csiro_variable_id`) VALUES (192,'2008-07-28 00:00:00',-3.1,2070,16,4);
