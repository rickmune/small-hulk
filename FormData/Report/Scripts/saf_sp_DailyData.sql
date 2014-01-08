DROP PROCEDURE [saf_sp_dailydata]
GO
Create PROCEDURE [saf_sp_dailydata]
    @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL ,   
  @respondentTypeId varchar(50)=NULL
AS
BEGIN
  if(@respondentTypeId='' or @respondentTypeId='ALL') set @respondentTypeId=null;
  IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
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
  where (1=1)
	and(@respondentTypeId is null or r.RespondentTypeId=@respondentTypeId)
	and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
  order by fi.[order]
	
END;

-- exec [saf_sp_dailydata]  @respondentTypeId='A9F14C73-045A-40F3-86B5-062F1EDE27AD'
--EXEC saf_sp_dailydata @startDate = '10-Jan-2013', @endDate = '10-Jan-2013'