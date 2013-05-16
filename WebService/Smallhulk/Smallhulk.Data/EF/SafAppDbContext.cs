using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Util;

namespace Smallhulk.Data.EF
{
   public class SafAppDbContext : DbContext
    {
       public SafAppDbContext() : base("DefaultConnection")
       {
          // this..ContextOptions.LazyLoadingEnabled = true;
       }

       public DbSet<User> Users { get; set; }
       public DbSet<Country> Countries { get; set; }
       protected override void OnModelCreating(DbModelBuilder modelBuilder)
       {
           modelBuilder.Conventions.Remove<OneToManyCascadeDeleteConvention>();

           modelBuilder.Entity<User>()
               .HasRequired(a => a.Country)
               .WithMany()
               .HasForeignKey(u => u.CountryId)
               .WillCascadeOnDelete(false); 
           base.OnModelCreating(modelBuilder);
       }
    }
   public class SafAppDbContextInitializer : DropCreateDatabaseIfModelChanges<SafAppDbContext>
    {
       protected override void Seed(SafAppDbContext context)
       {
           Country country = new Country
                                 {
                                     Code = "KE",
                                     CreatedOn = DateTime.Now,
                                     Id = Guid.NewGuid(),
                                     IsActive = true,
                                     UpdatedOn = DateTime.Now,
                                     Name = "Kenya",
                                     ZipCode = "+254"
                                 };

           context.Countries.Add(country);
           var user1 = new User
                           {
                               CreatedOn = DateTime.Now,
                               Email = "juvegitau@yahoo.com",
                               Fullname = "Juvenalis Gitau",
                               Id = Guid.NewGuid(),
                               IsActive = true,
                               Password = Md5Hash.GetMd5Hash("1234"),
                               PhoneNumber = "0722557538",
                               UpdatedOn = DateTime.Now,
                               Username = "gitau",
                               UserType = UserType.Email,
                               Country = country

                           };
           context.Users.Add(user1);
           var user2 = new User
                           {
                               CreatedOn = DateTime.Now,
                               Email = "rickmune@gmail.com",
                               Fullname = "Patrick Munene",
                               Id = Guid.NewGuid(),
                               IsActive = true,
                               Password = Md5Hash.GetMd5Hash("1234"),
                               PhoneNumber = "0000000000",
                               UpdatedOn = DateTime.Now,
                               Username = "maina",
                               UserType = UserType.Email,
                               Country = country
                           };
           context.Users.Add(user2);

       }
    }
}
