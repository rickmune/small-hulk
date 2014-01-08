DROP PROCEDURE [saf_sp_locations]
GO
Create PROCEDURE [saf_sp_locations]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Name
UNION
SELECT convert(varchar(50), id)Id,Name from tblLocation 
order by Name
     
	
END;