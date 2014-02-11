DROP PROCEDURE [saf_sp_users]
GO
Create PROCEDURE [saf_sp_users]   
 
AS
BEGIN
SELECT  'ALL' AS Id, 'ALL' AS Username ,'ALL' as Fullname ,1 as SortOrder
UNION 
SELECT convert(varchar(50), id)Id,Username,Fullname, 2 as SortOrder from tbluser
where UserType=2
 order by SortOrder, Username	
END;