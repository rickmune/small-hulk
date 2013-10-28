using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Domain.Locations;
using TDR.Core.Domain.Settings;
using TDR.Core.Util;

namespace TDR.Core.Repository
{
   public interface IConfigRepository
   {
       Config Load(ConfigType type);
       void Save(Config entity);
       ValidationResultInfo Validate(Config itemToValidate);

   }
}
