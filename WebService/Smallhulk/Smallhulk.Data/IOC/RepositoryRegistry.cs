using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Repository;
using Smallhulk.Data.EF;
using Smallhulk.Data.Repository;
using StructureMap.Configuration.DSL;

namespace Smallhulk.Data.IOC
{
  public class RepositoryRegistry:Registry
    {
      public RepositoryRegistry()
      {
          For<SafAppDbContext>()
                   .HybridHttpOrThreadLocalScoped()
                   .Use<SafAppDbContext>()
                   .Ctor<string>("connectionString")
                   .Is("DefaultConnection");
          For<IUserRepository>().Use<UserRepository>();
          For<ICountryRepository>().Use<CountryRepository>();
          For<IAccountRepository>().Use<AccountRepository>();
          For<ICategoryRepository>().Use<CategoryRepository>();
          For<IProductRepository>().Use<ProductRepository>();
          For<IRouteRepository>().Use<RouteRepository>();
          For<IOutletRepository>().Use<OutletRepository>();
      }
    }
}
