DROP PROCEDURE [saf_sp_towns]
GO
Create PROCEDURE [saf_sp_towns]   
 	 @locationId varchar(50)=NULL 
AS
BEGIN
 if(@locationId='' or @locationId='ALL') set @locationId=null;
SELECT  'ALL' AS Id, 'ALL' AS Name,1 as SortOrder
UNION
SELECT convert(varchar(50), id)Id,Name,2 as SortOrder from tblLocation  
where StructureId=3
and(@locationId is null or ParentId =@locationId)
order by SortOrder, Name
     
	
END;