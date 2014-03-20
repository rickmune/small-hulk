DROP PROCEDURE [saf_sp_outletCallByOutletTypeByRegion]
GO
Create PROCEDURE [saf_sp_outletCallByOutletTypeByRegion]   
 @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL  ,
	 @locationId varchar(50)=NULL  
AS
BEGIN
 IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
  if(@locationId='' or @locationId='ALL') set @locationId=null;
  with cte as (
    select t.Id , t .ParentId  ,t.Name,t .StructureId
    from tblLocation as t  
        where  t . StructureId=2 
		and(@locationId is null or t.Id =@locationId)
    union all
    select t.Id , t .parentid  ,c.Name,c .StructureId
    from cte as c
        inner join tblLocation as t on t.ParentId = c.Id              
)

select t.Name as Region, 
      rt.Name,	
		count(r.id) as Calls
		, ( case rt.Name 
		when 'DEALER' then 'LIME'
		when 'RETAIL' then 'ORANGE'
		when 'M-PESA & RETAIL' then 'YELLOW' 
		when 'M-PESA' then 'RED' end ) as Color
from cte t
 left join [dbo] .[dFormResult] r on r. LocationId= t.Id
 join [dFormRespondentType] rt on r.respondentTypeId=rt.Id  
  join dForms f on f.Id=rt.FormId
   where (1=1)
   and f.IdCode='SAF_D_D_F'
  and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate) 
   group by rt.Name , t.Name
 
END;