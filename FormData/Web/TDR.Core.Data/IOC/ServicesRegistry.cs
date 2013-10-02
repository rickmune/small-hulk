using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using StructureMap.Configuration.DSL;
using TDR.Core.Data.Services;
using TDR.Core.Services;

namespace TDR.Core.Data.IOC
{
    public class ServicesRegistry : Registry
    {
        public ServicesRegistry()
        {
            For<ISafaricomFormService>().Use<SafaricomFormService>();
        }
    }
}
