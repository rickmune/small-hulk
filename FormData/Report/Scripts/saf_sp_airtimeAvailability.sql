DROP PROCEDURE [saf_sp_airtimeavailability]
GO
Create PROCEDURE [saf_sp_airtimeavailability]   
 
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
where  q.idcode  in ('SAF_D_D_F_I_16','SAF_D_D_F_I_17','SAF_D_D_F_I_18','SAF_D_D_F_I_19','SAF_D_D_F_I_20','SAF_D_D_F_I_21','SAF_D_D_F_I_22')

group by q.Id,q.[Label],q.[order],HelpText
 order by q.[order]
	
END;