DROP PROCEDURE [saf_sp_locations]
GO
Create PROCEDURE [saf_sp_locations]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Name,1 as SortOrder
UNION
SELECT convert(varchar(50), id)Id,Name,2 as SortOrder from tblLocation  
where StructureId=2
order by SortOrder, Name
     
	
END;