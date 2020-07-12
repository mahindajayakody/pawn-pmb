CREATE TABLE pawn.`tblaudittrial` (
  `AUDITTRAILID` int(11) DEFAULT NULL COMMENT 'Audit Id',
  `TRNNO` varchar(50) DEFAULT NULL COMMENT 'Transaction No',
  `TABLENAME` varchar(50) DEFAULT NULL COMMENT 'Table Name',
  `PRIMARYKEY` varchar(50) DEFAULT NULL COMMENT 'Primary Key',
  `ACTION` varchar(20) DEFAULT NULL COMMENT 'Action Name',
  `TRANDATE` datetime DEFAULT NULL COMMENT 'Transaction  Date',
  `BEFORESAVEDATA` varchar(4000 ),
  `AFTERSAVEDATA`  varchar(4000 )
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
