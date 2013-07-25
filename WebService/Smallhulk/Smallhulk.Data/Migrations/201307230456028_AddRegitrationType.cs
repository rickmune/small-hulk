namespace Smallhulk.Data.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddRegitrationType : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Users", "RegistrationType", c => c.Int(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Users", "RegistrationType");
        }
    }
}
