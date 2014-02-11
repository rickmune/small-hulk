DROP PROCEDURE [saf_sp_traininglisting]
GO
Create PROCEDURE [saf_sp_traininglisting]
    @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL ,   
  @respondentTypeId varchar(50)=NULL,
  @username varchar(50)=NULL
 
AS
BEGIN
 set @username=LTRIM(RTRIM( @username)) ;
  if(@respondentTypeId='' or @respondentTypeId='ALL') set @respondentTypeId=null;
  if(@username='' or @username='ALL') set @username=null;

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
       PARSENAME(REPLACE(fi.section,'|','.'),1) SubSection,
	   (select [Fullname] from tbluser where Username= r.username)  Fullname,
	   r.dateinserted
  FROM [dFormResult] r
  left join dFormResultItem ri on ri.formresultid=r.id
  left join dFormItems fi on fi.id=ri.FormItemId
  left join dFormRespondentType ft on ft.id=r.RespondentTypeId 
  left join dForms f on f.Id= ft.FormId
  where (1=1)
    and f.IdCode='SAF_ST'
	and(@respondentTypeId is null or r.RespondentTypeId=@respondentTypeId)
	and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
	and(@username is null or r.username=@username)
  order by fi.[order]
	
END;

-- exec [saf_sp_traininglisting]  @respondentTypeId='A9F14C73-045A-40F3-86B5-062F1EDE27AD'
--EXEC saf_sp_dailydata @username = ' ALL'