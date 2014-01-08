DROP PROCEDURE [saf_sp_respondentTypes]
GO
Create PROCEDURE [saf_sp_respondentTypes]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Name
UNION
SELECT convert(varchar(50), id)Id,Name from dFormRespondentType 
order by Name
     
	
END;