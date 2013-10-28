CREATE TABLE [dbo].[tblReportGroup](
	[Id] [uniqueidentifier] NOT NULL PRIMARY KEY,
	[Name] [nvarchar](250) NOT NULL,	
	[Description] [nvarchar](250)  NULL,
	[ClientId] [uniqueidentifier] not null,	
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[IsActive] [bit] NOT NULL
	);
ALTER TABLE [dbo].[tblReportGroup] ADD CONSTRAINT ReportGroup_Client_FK FOREIGN KEY ([ClientId]) REFERENCES tblClient(Id);
CREATE TABLE [dbo].[tblReportItem](
	[Id] [uniqueidentifier] NOT NULL PRIMARY KEY,
	[Name] [nvarchar](250) NOT NULL,	
	[Description] [nvarchar](250)  NULL,	
	[ReportUrl] [nvarchar](250)  NOT NULL,
	[ReportOrder] int Not  NULL,
	[GroupId] [uniqueidentifier] not null,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[IsActive] [bit] NOT NULL
	);	
ALTER TABLE [dbo].[tblReportItem] ADD CONSTRAINT ReportItem_Group_FK FOREIGN KEY ([GroupId]) REFERENCES [tblReportGroup](Id);