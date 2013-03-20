ALTER TABLE `seaports_test`.`dataelement_csirodata` DROP FOREIGN KEY `FKC9ECC6738192BB97` , DROP FOREIGN KEY `FKC9ECC673DD18A2F` ;

ALTER TABLE `seaports_test`.`dataelement_csirodata` 

  ADD CONSTRAINT `FKC9ECC6738192BB97`

  FOREIGN KEY (`DataElement_Id` )

  REFERENCES `seaports_test`.`dataelement` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FKC9ECC673DD18A2F`

  FOREIGN KEY (`CsiroData_Id` )

  REFERENCES `seaports_test`.`csirodata` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;



ALTER TABLE `seaports_test`.`dataelement_engineeringmodeldata` DROP FOREIGN KEY `FK3035230DC9652565` , DROP FOREIGN KEY `FK3035230D907E0245` ;

ALTER TABLE `seaports_test`.`dataelement_engineeringmodeldata` 

  ADD CONSTRAINT `FK3035230DC9652565`

  FOREIGN KEY (`DataElement_Id` )

  REFERENCES `seaports_test`.`dataelement` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FK3035230D907E0245`

  FOREIGN KEY (`EngineeringModelData_Id` )

  REFERENCES `seaports_test`.`engineeringmodeldata` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;



ALTER TABLE `seaports_test`.`dataelement_cmardata` DROP FOREIGN KEY `FK9219EA5251873CC5` , DROP FOREIGN KEY `FK9219EA525C15E2A` ;

ALTER TABLE `seaports_test`.`dataelement_cmardata` 

  ADD CONSTRAINT `FK9219EA5251873CC5`

  FOREIGN KEY (`CmarData_Id` )

  REFERENCES `seaports_test`.`cmardata` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FK9219EA525C15E2A`

  FOREIGN KEY (`DataElement_Id` )

  REFERENCES `seaports_test`.`dataelement` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;

