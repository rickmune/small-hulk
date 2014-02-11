DROP PROCEDURE [saf_sp_dsb_competitorAirtimeavailability]
GO
Create PROCEDURE [saf_sp_dsb_competitorAirtimeavailability]   
@startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL  
AS
BEGIN
IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;

SELECT 
     q.HelpText,
	  (case r.FormItemAnswer when '1' then 'YES' when '0' then 'NO' end ) as CompetitorAvailability,
	  count(case r.FormItemAnswer when '1' then r.FormItemAnswer when '0' then r.FormItemAnswer end)  as TotalCount     
  FROM [dFormItems] q
  right join [dFormResultItem] r on r.FormItemId=q.Id
   join dFormResult f on f.Id=r.FormResultId
where  q.idcode  in ('SAF_D_D_F_I_23','SAF_D_D_F_I_24','SAF_D_D_F_I_25')
 and  ((@startDate is null or @endDate is null) or f.DateInserted between @startDate and  @endDate) 
group by r.FormItemAnswer,q.HelpText

	
END;