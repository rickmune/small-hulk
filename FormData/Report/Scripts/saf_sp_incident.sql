DROP PROCEDURE [saf_sp_incident]
GO
Create PROCEDURE [saf_sp_incident]
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
     
	  (case  when fi.[order]= 1 AND isdate(ri.formitemanswer)=1 then convert(nvarchar(30),cast(ri.formitemanswer as datetime),103) when fi.[order]= 1 then ''   else  ri.FormItemAnswer end) formitemanswer,
      r. id  ,
      fi.helptext,
     
	   (select [Fullname] from tbluser where Username= r.username)  Fullname,
	     r.dateinserted
  FROM [dFormResult] r
  left join dFormResultItem ri on ri.formresultid=r.id
  left join dFormItems fi on fi.id=ri.FormItemId
  left join dFormRespondentType ft on ft.id=r.RespondentTypeId 
  left join dForms f on f.Id= ft.FormId
  where (1=1)
    and f.IdCode='SAF_A_I'
	and(@respondentTypeId is null or r.RespondentTypeId=@respondentTypeId)
	and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
	and(@username is null or r.username=@username)
  order by fi.[order]
	
END;

-- exec [saf_sp_dailydata]  @respondentTypeId='A9F14C73-045A-40F3-86B5-062F1EDE27AD'
--EXEC saf_sp_dailydata @username = ' ALL'


--declare @date nvarchar(max) = '2013:03:02T16:48:00'

--select convertedDate = CONVERT( datetime,@date)