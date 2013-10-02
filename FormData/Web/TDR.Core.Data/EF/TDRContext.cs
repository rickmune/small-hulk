using System;
using System.Data.Entity;
using TDR.Core.Domain.Clients;
using TDR.Core.Domain.Forms;
using TDR.Core.Domain.Users;

namespace TDR.Core.Data.EF
{
    public class TDRContext:DbContext
    {
         public TDRContext()
            : base("TDRContextConnection")
       {
          // this..ContextOptions.LazyLoadingEnabled = true;
       }
         public TDRContext(string connectionString)
            : base(connectionString)
        {
            Console.WriteLine("");
        }
         public DbSet<DformEntity> Forms { get; set; }
         public DbSet<DformItemEntity> FormItems{ get; set; }
         public DbSet<DformRespondentTypeEntity> FormRespondentTypes { get; set; }
         public DbSet<DformItemRespondentTypeEntity> FormItemRespondentTypes { get; set; }
         public DbSet<DformItemAnswerEntity> FormItemAnswers { get; set; }
         public DbSet<DformResultEntity> FormResult { get; set; }
         public DbSet<DformResultItemEntity> FormResultItem { get; set; }
         public DbSet<User> Users { get; set; }
         public DbSet<Client> Clients { get; set; }


         protected override void OnModelCreating(DbModelBuilder modelBuilder)
         {
             base.OnModelCreating(modelBuilder);
             modelBuilder.Entity<DformItemEntity>()
                 .HasRequired(x => x.Form)
                 .WithMany(x => x.FormItems)
                 .HasForeignKey(x => x.FormId)
                 .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformRespondentTypeEntity>()
                 .HasRequired(x => x.Form)
                 .WithMany(x => x.RespondentTypes)
                 .HasForeignKey(x => x.FormId)
                 .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformItemRespondentTypeEntity>()
                 .HasRequired(x => x.FormRespondentType)
                 .WithMany(x => x.FormItemRespondentTypes)
                 .HasForeignKey(x => x.FormRespondentTypeId)
                 .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformItemRespondentTypeEntity>()
                 .HasRequired(x => x.FormItem)
                 .WithMany(x => x.FormItemRespondentTypes)
                 .HasForeignKey(x => x.FormItemId)
                 .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformItemAnswerEntity>()
                 .HasRequired(x => x.FormItem)
                 .WithMany(x => x.FormItemAnswers)
                 .HasForeignKey(x => x.FormItemId)
                 .WillCascadeOnDelete(false);

             modelBuilder.Entity<DformResultEntity>()
                .HasRequired(x => x.Form)
                .WithMany(x => x.FormResult)
                .HasForeignKey(x => x.FormId)
                .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformResultEntity>()
                 .HasRequired(x => x.RespondentType)
                 .WithMany(x => x.FormResult)
                 .HasForeignKey(x => x.RespondentTypeId)
                 .WillCascadeOnDelete(false);

             modelBuilder.Entity<DformResultItemEntity>()
               .HasRequired(x => x.FormItem)
               .WithMany(x => x.FormItemResultItems)
               .HasForeignKey(x => x.FormItemId)
               .WillCascadeOnDelete(false);
             modelBuilder.Entity<DformResultItemEntity>()
               .HasRequired(x => x.FormResult)
               .WithMany(x => x.FormItemResultItems)
               .HasForeignKey(x => x.FormResultId)
               .WillCascadeOnDelete(false);

             modelBuilder.Entity<User>()
              .HasOptional(x => x.Client)
              .WithMany(x => x.Users)
              .HasForeignKey(x => x.ClientId)
              .WillCascadeOnDelete(false);

             modelBuilder.Entity<DformEntity>()
             .HasRequired(x => x.Client)
             .WithMany(x => x.Forms)
             .HasForeignKey(x => x.ClientId)
             .WillCascadeOnDelete(false);


         }
    }
}