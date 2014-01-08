DROP PROCEDURE [saf_sp_outletcallperuser]
GO
Create PROCEDURE [saf_sp_outletcallperuser]   
 @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL    
AS
BEGIN
 IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
SELECT        
     rt.Name,
	 r.Username,
	count(r.id) as Calls
  FROM dFormResult r
  join [dFormRespondentType] rt on r.respondentTypeId=rt.Id
   where (1=1)
  and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
  group by rt.Name ,r.Username
END;