CREATE TABLE [tblLocation](
	[Id] [uniqueidentifier] NOT NULL PRIMARY KEY,
	[Name] [nvarchar](250) NOT NULL,
	[Code] [nvarchar](250) NOT NULL,
	[Description] [nvarchar](250)  NULL,
	[ParentId] [uniqueidentifier] NULL,
	[StructureId] int NULL,
	[CreatedOn] [datetime] NOT NULL,
	[UpdatedOn] [datetime] NOT NULL,
	[IsActive] [bit] NOT NULL
	);
	
Alter table  tblUser add LocationId  uniqueidentifier null;

ALTER TABLE tblUser ADD CONSTRAINT User_Location_FK FOREIGN KEY (LocationId) REFERENCES tblLocation(Id);