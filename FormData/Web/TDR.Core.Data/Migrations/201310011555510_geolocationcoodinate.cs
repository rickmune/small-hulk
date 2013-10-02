namespace TDR.Core.Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class geolocationcoodinate : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.dFormResult", "Longitude", c => c.Double());
            AddColumn("dbo.dFormResult", "Latitude", c => c.Double());
            DropColumn("dbo.dFormResult", "Location");
        }
        
        public override void Down()
        {
            AddColumn("dbo.dFormResult", "Location", c => c.Geography());
            DropColumn("dbo.dFormResult", "Latitude");
            DropColumn("dbo.dFormResult", "Longitude");
        }
    }
}
