namespace Smallhulk.Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Username = c.String(nullable: false),
                        Password = c.String(),
                        Fullname = c.String(nullable: false),
                        UserType = c.Int(nullable: false),
                        Email = c.String(),
                        PhoneNumber = c.String(),
                        AccountId = c.Guid(nullable: false),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Accounts", t => t.AccountId)
                .Index(t => t.AccountId);
            
            CreateTable(
                "dbo.Accounts",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(nullable: false),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Countries",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Code = c.String(nullable: false),
                        ZipCode = c.String(nullable: false),
                        Name = c.String(nullable: false),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Categories",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(nullable: false),
                        Description = c.String(),
                        AccountId = c.Guid(nullable: false),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Accounts", t => t.AccountId)
                .Index(t => t.AccountId);
            
            CreateTable(
                "dbo.Products",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(nullable: false),
                        Description = c.String(),
                        CategoryId = c.Guid(nullable: false),
                        AccountId = c.Guid(nullable: false),
                        BuyingPrice = c.Decimal(nullable: false, precision: 18, scale: 2),
                        SellingPrice = c.Decimal(nullable: false, precision: 18, scale: 2),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Categories", t => t.CategoryId)
                .ForeignKey("dbo.Accounts", t => t.AccountId)
                .Index(t => t.CategoryId)
                .Index(t => t.AccountId);
            
            CreateTable(
                "dbo.Routes",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        AccountId = c.Guid(nullable: false),
                        Name = c.String(nullable: false),
                        Code = c.String(),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Accounts", t => t.AccountId)
                .Index(t => t.AccountId);
            
            CreateTable(
                "dbo.Outlets",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        AccountId = c.Guid(nullable: false),
                        RouteId = c.Guid(nullable: false),
                        Name = c.String(nullable: false),
                        Code = c.String(),
                        Longitude = c.Double(nullable: false),
                        Latitude = c.Double(nullable: false),
                        CreatedOn = c.DateTime(nullable: false),
                        UpdatedOn = c.DateTime(nullable: false),
                        IsActive = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Accounts", t => t.AccountId)
                .ForeignKey("dbo.Routes", t => t.RouteId)
                .Index(t => t.AccountId)
                .Index(t => t.RouteId);
            
        }
        
        public override void Down()
        {
            DropIndex("dbo.Outlets", new[] { "RouteId" });
            DropIndex("dbo.Outlets", new[] { "AccountId" });
            DropIndex("dbo.Routes", new[] { "AccountId" });
            DropIndex("dbo.Products", new[] { "AccountId" });
            DropIndex("dbo.Products", new[] { "CategoryId" });
            DropIndex("dbo.Categories", new[] { "AccountId" });
            DropIndex("dbo.Users", new[] { "AccountId" });
            DropForeignKey("dbo.Outlets", "RouteId", "dbo.Routes");
            DropForeignKey("dbo.Outlets", "AccountId", "dbo.Accounts");
            DropForeignKey("dbo.Routes", "AccountId", "dbo.Accounts");
            DropForeignKey("dbo.Products", "AccountId", "dbo.Accounts");
            DropForeignKey("dbo.Products", "CategoryId", "dbo.Categories");
            DropForeignKey("dbo.Categories", "AccountId", "dbo.Accounts");
            DropForeignKey("dbo.Users", "AccountId", "dbo.Accounts");
            DropTable("dbo.Outlets");
            DropTable("dbo.Routes");
            DropTable("dbo.Products");
            DropTable("dbo.Categories");
            DropTable("dbo.Countries");
            DropTable("dbo.Accounts");
            DropTable("dbo.Users");
        }
    }
}
