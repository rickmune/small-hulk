CREATE TABLE [dbo].[tblConfig](
	[Id] [uniqueidentifier] NOT NULL PRIMARY KEY,
	[Value] [nvarchar](250) NOT NULL,	
	[Description] [nvarchar](250)  NULL,	
	[ConfigType] int  NOT NULL,	
	[ClientId] [uniqueidentifier]  null	
	);	