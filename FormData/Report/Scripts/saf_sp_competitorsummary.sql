DROP PROCEDURE [saf_sp_competitorsummary]
GO
Create PROCEDURE [saf_sp_competitorsummary]
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

  with cte as (
    select t.Id , t .ParentId  ,t.Name,t .StructureId, t.Id as mainId
    from tblLocation as t  
        where  t . StructureId=2 		
    union all
    select t.Id , t.parentid  ,c.Name,c .StructureId,c.mainId 
    from cte as c
        inner join tblLocation as t on t.ParentId = c.Id              
)
 --[saf_sp_competitorsummary]
SELECT 
     t.Name,      
      ft.name as OutletType,
      fi.label,
      fi.[order],
      fi.idcode, 
	
	  PARSENAME(REPLACE(fi.section,'|','.'),2) SectionOrder,
       PARSENAME(REPLACE(fi.section,'|','.'),1) Section,
	 sum ((case  when fi.[order]!= 1 and ISNUMERIC(ri.formitemanswer)=1  then cast(ri.formitemanswer as int)   else  NULL end)) total,
       
	--  (case  when fi.[order]= 1 AND isdate(ri.formitemanswer)=1 then convert(nvarchar(30),cast(ri.formitemanswer as datetime),103)    else  NULL end) formitemanswer,

      fi.helptext
  
  FROM cte t
  left join  [dFormResult] r on r.LocationId=t.Id
  left join dFormResultItem ri on ri.formresultid=r.id
  left join dFormItems fi on fi.id=ri.FormItemId
  left join dFormRespondentType ft on ft.id=r.RespondentTypeId 
  left join dForms f on f.Id= ft.FormId
  where (1=1)
  and fi.[order]!= 1
    and f.IdCode='SAF_C_S'
	and(@respondentTypeId is null or r.RespondentTypeId=@respondentTypeId)
	and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
	and(@username is null or r.username=@username)
	group by t.Name,           ft.name,      fi.label,      fi.[order],      fi.idcode,fi.helptext, fi.Section
  order by fi.[order]
	
END;

-- exec [saf_sp_dailydata]  @respondentTypeId='A9F14C73-045A-40F3-86B5-062F1EDE27AD'
--EXEC saf_sp_dailydata @username = ' ALL'


--declare @date nvarchar(max) = '2013:03:02T16:48:00'

--select convertedDate = CONVERT( datetime,@date)