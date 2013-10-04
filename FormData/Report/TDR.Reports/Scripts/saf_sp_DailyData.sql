DROP PROCEDURE [saf_sp_dailydata]
GO
Create PROCEDURE [saf_sp_dailydata]   
 
AS
BEGIN

SELECT 
      r.username,
      ft.name as OutletType,
      fi.label,
      fi.[order],
      fi.idcode,
      ri.formitemanswer  ,
      r. id  ,
      fi.helptext,
      PARSENAME(REPLACE(fi.section,'|','.'),4) MainSectionOrder,
       PARSENAME(REPLACE(fi.section,'|','.'),3) MainSection,
       PARSENAME(REPLACE(fi.section,'|','.'),2) SubSectionOrder,
       PARSENAME(REPLACE(fi.section,'|','.'),1) SubSection
  FROM [dFormResult] r
  left join dFormResultItem ri on ri.formresultid=r.id
  left join dFormItems fi on fi.id=ri.FormItemId
  left join dFormRespondentType ft on ft.id=r.RespondentTypeId
  
  order by fi.[order]
	
END;