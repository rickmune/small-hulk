DROP PROCEDURE [saf_sp_respondentTypes]
GO
Create PROCEDURE [saf_sp_respondentTypes]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Name,1 as SortOrder
UNION
SELECT convert(varchar(50), fr.id)Id,fr.Name ,2 as SortOrder
 from dFormRespondentType fr
left join dForms f on f.id=fr.FormId
 where f.IdCode='SAF_D_D_F'
  order by SortOrder, Name
     
	
END;