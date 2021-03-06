﻿using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Data.Entity.Validation;
using System.Diagnostics;
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
       public SafAppDbContext(string connectionString)
            : base(connectionString)
        {
            Console.WriteLine("");
        }
       public override int SaveChanges()
       {
           try
           {
               return base.SaveChanges();
           }
           catch (DbEntityValidationException dbEx)
           {
               foreach (var validationErrors in dbEx.EntityValidationErrors)
               {
                   foreach (var validationError in validationErrors.ValidationErrors)
                   {
                       Trace.TraceInformation("Class: {0}, Property: {1}, Error: {2}",
                           validationErrors.Entry.Entity.GetType().FullName,
                           validationError.PropertyName,
                           validationError.ErrorMessage);
                   }
               }

               throw;  // You can also choose to handle the exception here...
           }
       }
       public DbSet<User> Users { get; set; }
       public DbSet<Country> Countries { get; set; }
       public DbSet<Category> Categories { get; set; }
       public DbSet<Product> Products { get; set; }
       public DbSet<Account> Accounts { get; set; }
       public DbSet<Route> Routes { get; set; }
       public DbSet<Outlet> Outlets { get; set; } 
       protected override void OnModelCreating(DbModelBuilder modelBuilder)
       {
           modelBuilder.Conventions.Remove<OneToManyCascadeDeleteConvention>();

         
           modelBuilder.Entity<User>()
              .HasRequired(a => a.Account)
              .WithMany()
              .HasForeignKey(u => u.AccountId)
              .WillCascadeOnDelete(false);
           modelBuilder.Entity<Category>()
              .HasRequired(a => a.Account)
              .WithMany()
              .HasForeignKey(u => u.AccountId)
              .WillCascadeOnDelete(false);
           //modelBuilder.Entity<Product>()
           //   .HasRequired(a => a.Account)
           //   .WithMany()
           //   .HasForeignKey(u => u.AccountId)
           //   .WillCascadeOnDelete(false);
           //modelBuilder.Entity<Product>()
           //   .HasRequired(a => a.Category)
           //   .WithMany()
           //   .HasForeignKey(u => u.CategoryId)
           //   .WillCascadeOnDelete(false); 
           base.OnModelCreating(modelBuilder);
       }
    }
   public class SafAppDbContextInitializer 
    {
       public void Seed()
       {
           this.Seed(new SafAppDbContext());
       }

       protected  void Seed(SafAppDbContext context)
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
           Account account = new Account
           {
               
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Test Account",
               
           };

           context.Accounts.Add(account);
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
                               UserType = UserType.Admin,
                             
                               Account = account

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
                               UserType = UserType.Admin,
                               Account = account
                           };
           context.Users.Add(user2);
           
           var category = new Category
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Sugar",
               Description = "Sugar",
               Account = account
           };

           context.Categories.Add(category);
          
           var product1 = new Product()
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Mumias Sugar 1Kg",
               Account = account,
               Category = category,
               BuyingPrice = 20,
               SellingPrice = 30,
               Description = "Sugar"


           };
           context.Products.Add(product1);
           var product2 = new Product()
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Sony Sugar 1Kg",
               Account = account,
               Category = category,
               BuyingPrice = 20,
               SellingPrice = 30,
           };
           context.Products.Add(product2);
           var route = new Route()
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Kilimani",
               Account = account,
               AccountId = account.Id,
               Code = "R001"
           };
           context.Routes.Add(route);
           var outlet = new Outlet()
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Outlet Yaya",
               Account = account,
              AccountId = account.Id,
              Code = "O001",
              Latitude = 31.22,
              Longitude = 1.22,
              Route = route,
              RouteId = route.Id
           };
           context.Outlets.Add(outlet);
           var outlet1 = new Outlet()
           {
               CreatedOn = DateTime.Now,
               Id = Guid.NewGuid(),
               IsActive = true,
               UpdatedOn = DateTime.Now,
               Name = "Outlet Ronga",
               Account = account,
               AccountId = account.Id,
               Code = "O002",
               Latitude = 31.32,
               Longitude = 1.32,
               Route = route,
               RouteId = route.Id
           };
           context.Outlets.Add(outlet1);
           context.SaveChanges();
       }
    }
}
