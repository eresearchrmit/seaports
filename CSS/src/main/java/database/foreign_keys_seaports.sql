ALTER TABLE `seaports`.`data_element_csiro_data` DROP FOREIGN KEY `FKEC1C902B505D479C` , DROP FOREIGN KEY `FKEC1C902BED6FB022` ;

ALTER TABLE `seaports`.`data_element_csiro_data` 

  ADD CONSTRAINT `FKEC1C902B505D479C`

  FOREIGN KEY (`csiro_data_id` )

  REFERENCES `seaports`.`csiro_data` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FKEC1C902BED6FB022`

  FOREIGN KEY (`data_element_id` )

  REFERENCES `seaports`.`data_element` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;


  
  ALTER TABLE `seaports`.`data_element_engineering_model_data` DROP FOREIGN KEY `FK610A3B84354219F0` , DROP FOREIGN KEY `FK610A3B8437160FB9` ;

ALTER TABLE `seaports`.`data_element_engineering_model_data` 

  ADD CONSTRAINT `FK610A3B84354219F0`

  FOREIGN KEY (`data_element_id` )

  REFERENCES `seaports`.`data_element` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FK610A3B8437160FB9`

  FOREIGN KEY (`engineering_model_data_id` )

  REFERENCES `seaports`.`engineering_model_data` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;


  ALTER TABLE `seaports`.`data_element_cmar_data` DROP FOREIGN KEY `FKAE352C76CB8CE91C` , DROP FOREIGN KEY `FKAE352C76719E52B5` ;

ALTER TABLE `seaports`.`data_element_cmar_data` 

  ADD CONSTRAINT `FKAE352C76CB8CE91C`

  FOREIGN KEY (`cmar_data_id` )

  REFERENCES `seaports`.`cmar_data` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FKAE352C76719E52B5`

  FOREIGN KEY (`data_element_id` )

  REFERENCES `seaports`.`data_element` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;

