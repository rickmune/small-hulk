using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using TDR.Core.Data.EF;


namespace TDR.EF
{
    public class DatabaseInitializer : CreateDatabaseIfNotExists<TDRContext>
    {
        protected override void Seed(TDRContext context)
        {
           
        }
    }
}