DROP PROCEDURE [saf_sp_respondentTypes]
GO
Create PROCEDURE [saf_sp_respondentTypes]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Name
UNION
SELECT convert(varchar(50), fr.id)Id,fr.Name from dFormRespondentType fr
left join dForms f on f.id=fr.FormId
 where f.IdCode='SAF_ST'
  order by Name
     
	
END;