DROP PROCEDURE [saf_sp_outlettypecall]
GO
Create PROCEDURE [saf_sp_outlettypecall]   
 @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL  
	 
AS
BEGIN
 IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
 
SELECT        
     rt.Name,	
		count(r.id) as Calls
		, ( case rt.Name 
		when 'DEALER' then 'LIME'
		when 'RETAIL' then 'ORANGE'
		when 'M-PESA & RETAIL' then 'YELLOW' 
		when 'M-PESA' then 'RED' end ) as Color
  FROM dFormResult r
  join [dFormRespondentType] rt on r.respondentTypeId=rt.Id
  
  join dForms f on f.Id=rt.FormId
   where (1=1)
   and f.IdCode='SAF_D_D_F'
  and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)  
  group by rt.Name 
END;