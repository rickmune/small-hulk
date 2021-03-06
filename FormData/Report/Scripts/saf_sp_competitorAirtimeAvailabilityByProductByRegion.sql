DROP PROCEDURE [saf_sp_competitorAirtimeAvailabilityByProductByRegion]
GO
Create PROCEDURE [saf_sp_competitorAirtimeAvailabilityByProductByRegion]   
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

select t.Name,q.[Label],q.[HelpText],q.[Order] ,    
	 count (case ri.FormItemAnswer when '1' then ri.FormItemAnswer when '0' then null  end)  as TotalCount,
	  (case q.[HelpText] when 'Orange' then 'Orange'
	   when 'YU' then 'GREEN'
	   when 'AIRTEL' then 'RED'
	     end)  as Color  
from cte t
 left join [dbo] .[dFormResult] r on r. LocationId= t.Id
 left join [dFormResultItem] ri on ri.FormResultId=r.Id
  left join [dFormItems] q on q.Id=ri.FormItemId
 where q.idcode  in ('SAF_D_D_F_I_23','SAF_D_D_F_I_24','SAF_D_D_F_I_25')   
 and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate) 
 
group by ri.FormItemAnswer,t.Name,q.[Label],q.[HelpText],q.[Order]
 
END;