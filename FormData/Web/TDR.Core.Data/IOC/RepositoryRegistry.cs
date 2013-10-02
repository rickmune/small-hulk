using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using StructureMap.Configuration.DSL;
using TDR.Core.Data.EF;
using TDR.Core.Data.Repository.Clients;
using TDR.Core.Data.Repository.Users;
using TDR.Core.Data.Services;
using TDR.Core.Repository;
using TDR.Core.Services;
using TDR.EF;

namespace TDR.Core.Data.IOC
{
    public class RepositoryRegistry : Registry
    {
        public RepositoryRegistry()
        {
            For<TDRContext>()
                    .HybridHttpOrThreadLocalScoped()
                    .Use<TDRContext>()
                    .Ctor<string>("connectionString")
                    .Is("TDRContextConnection");
            For<IUserRepository>().Use<UserRepository>();
            For<IClientRepository>().Use<ClientRepository>();

        }
    }
}
