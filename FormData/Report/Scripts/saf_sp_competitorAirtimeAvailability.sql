DROP PROCEDURE [saf_sp_competitorAirtimeavailability]
GO
Create PROCEDURE [saf_sp_competitorAirtimeavailability]   
 
AS
BEGIN

SELECT        
      q.Id     
      ,q.[Label],
	  q.HelpText,
	  q.[Order],
	  count(case r.FormItemAnswer when '1' then r.FormItemAnswer else null end)  as TotalCount     
  FROM [dFormItems] q
  right join [dFormResultItem] r on r.FormItemId=q.Id
where  q.idcode  in ('SAF_D_D_F_I_23','SAF_D_D_F_I_24','SAF_D_D_F_I_25')

group by q.Id,q.[Label],q.[order],HelpText
 order by q.[order]
	
END;