DROP PROCEDURE [saf_sp_dsb_airtimeavailability]
GO
Create PROCEDURE [saf_sp_dsb_airtimeavailability]   
 @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL  
AS
BEGIN
IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
SELECT  
	 ( case r.FormItemAnswer when '1' then 'YES' when '0' then 'NO' end ) as AirTimeAvailability,
	  count(case r.FormItemAnswer when '1' then r.FormItemAnswer when '0' then r.FormItemAnswer end)  as TotalCount     
  FROM [dFormItems] q
  right join [dFormResultItem] r on r.FormItemId=q.Id
  join dFormResult f on f.Id=r.FormResultId
where  q.idcode  in ('SAF_D_D_F_I_16','SAF_D_D_F_I_17','SAF_D_D_F_I_18','SAF_D_D_F_I_19','SAF_D_D_F_I_20','SAF_D_D_F_I_21','SAF_D_D_F_I_22')
 and  ((@startDate is null or @endDate is null) or f.DateInserted between @startDate and  @endDate) 
group by  r.FormItemAnswer

	
END;