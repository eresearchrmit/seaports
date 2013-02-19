ALTER TABLE `seaports`.`dataelement_csirodata` DROP FOREIGN KEY `FKC9ECC6738192BB97` , DROP FOREIGN KEY `FKC9ECC673DD18A2F` ;

ALTER TABLE `seaports`.`dataelement_csirodata` 

  ADD CONSTRAINT `FKC9ECC6738192BB97`

  FOREIGN KEY (`DataElement_Id` )

  REFERENCES `seaports`.`dataelement` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FKC9ECC673DD18A2F`

  FOREIGN KEY (`CsiroData_Id` )

  REFERENCES `seaports`.`csirodata` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;

ALTER TABLE `seaports`.`dataelement_engineeringmodeldata` DROP FOREIGN KEY `FK3035230DC9652565` , DROP FOREIGN KEY `FK3035230D907E0245` ;

ALTER TABLE `seaports`.`dataelement_engineeringmodeldata` 

  ADD CONSTRAINT `FK3035230DC9652565`

  FOREIGN KEY (`DataElement_Id` )

  REFERENCES `seaports`.`dataelement` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE, 

  ADD CONSTRAINT `FK3035230D907E0245`

  FOREIGN KEY (`EngineeringModelData_Id` )

  REFERENCES `seaports`.`engineeringmodeldata` (`id` )

  ON DELETE CASCADE

  ON UPDATE CASCADE;

