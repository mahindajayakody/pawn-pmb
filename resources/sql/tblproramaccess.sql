INSERT INTO `pawn`.`tblsystemprogram` (`PRGID`, `PRDCODE`, `PARENTID`, `NODENAME`, `URLPATH`, `ACCESS`) VALUES ('10007', 'PW', '45', 'Re-Set Password', 'resetPasswordService.do', '1');
UPDATE `pawn`.`tblsystemprogram` SET `ACCESS`='2' WHERE `PRGID`='10004';
UPDATE `pawn`.`tblsystemprogram` SET `ACCESS`='2' WHERE `PRGID`='10007';
UPDATE `pawn`.`tblsystemprogram` SET `URLPATH`='auditTrail.do' WHERE `PRGID`='10006';

