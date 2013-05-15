using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Repository;
using Smallhulk.Data.Repository;
using StructureMap.Configuration.DSL;

namespace Smallhulk.Data.IOC
{
  public class RepositoryRegistry:Registry
    {
      public RepositoryRegistry()
      {
          For<IUserRepository>().Use<UserRepository>();
      }
    }
}
