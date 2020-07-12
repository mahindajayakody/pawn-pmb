ALTER TABLE `pawn`.`tblofficer` 
DROP COLUMN `VALIEDPD`;

ALTER TABLE `pawn`.`tblofficer` 
ADD COLUMN `VALIEDPD` DATE NULL AFTER `PASSRPTTIME`;

update `pawn`.`tblofficer` set VALIEDPD = '2018-06-30';
update `pawn`.`tblofficer` set PASSRPTTIME = 3;

