using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Web.Lib.DTOBuilders;
using Smallhulk.Web.Lib.DTOBuilders.Impl;
using StructureMap.Configuration.DSL;

namespace Smallhulk.Web.Lib.IOC
{
   public class BuilderRegistry:Registry
    {
       public BuilderRegistry()
       {
           For<IDataTransferBuilder>().Use<DataTransferBuilder>();
       }
    }
}
