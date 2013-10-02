namespace TDR.Core.Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class addvalidationatrributes : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.dFormItems", "ValidationText", c => c.String());
            AddColumn("dbo.dFormItems", "ValidationRegex", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.dFormItems", "ValidationRegex");
            DropColumn("dbo.dFormItems", "ValidationText");
        }
    }
}
