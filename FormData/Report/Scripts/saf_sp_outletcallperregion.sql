DROP PROCEDURE [saf_sp_outletcallperregion]
GO
Create PROCEDURE [saf_sp_outletcallperregion]   
 @startDate varchar(50)=NULL,
    @endDate varchar(50)=NULL  ,
	 @locationId varchar(50)=NULL  
AS
BEGIN
 IF(isdate(@startDate)=0) SET @startDate=null;
  IF(isdate(@endDate)=0)	SET @endDate=null;
  if(@locationId='' or @locationId='ALL') set @locationId=null;
SELECT        
     rt.Name,
	 l.Name as Region,
	count(r.id) as Calls
  FROM dFormResult r
  join [dFormRespondentType] rt on r.respondentTypeId=rt.Id
  join [tblLocation] l on r.LocationId=l.Id
  join dForms f on f.Id=rt.FormId
   where (1=1)
   and f.IdCode='SAF_D_D_F'
  and  ((@startDate is null or @endDate is null) or r.DateInserted between @startDate and  @endDate)
  and(@locationId is null or r.LocationId=@locationId)
  group by rt.Name ,l.Name
END;