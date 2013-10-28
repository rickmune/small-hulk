using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Data.EF;
using TDR.Core.Domain.Locations;
using TDR.Core.Domain.Settings;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.Settings
{
   public class ConfigRepository : IConfigRepository
    {
       private TDRContext _context;

       public ConfigRepository(TDRContext context)
       {
           _context = context;
       }


       public Config Load(ConfigType type)
       {
           return _context.Configurations.FirstOrDefault(s => s.ConfigType == type);
       }

       public void Save(Config entity)
       {
           ValidationResultInfo vri = Validate(entity);
           if (!vri.IsValid)
               throw new DomainValidationException(vri, "Configurations  Details not Valid");
           DateTime date = DateTime.Now;
           Config tbl = _context.Configurations.FirstOrDefault(s => s.ConfigType == entity.ConfigType);
           if (tbl == null)
           {
               tbl = new Config();
               tbl.ConfigType = entity.ConfigType;
               tbl.Id = Guid.NewGuid();
               _context.Configurations.Add(tbl);
           }
           tbl.Value = entity.Value;

           tbl.Description = entity.Description;
           tbl.Value = entity.Value;
           tbl.ConfigType = entity.ConfigType;
           tbl.ClientId = entity.ClientId ?? null;
           _context.SaveChanges();
       }

       public ValidationResultInfo Validate(Config itemToValidate)
       {
           return itemToValidate.BasicValidation();
       }
    }
}
