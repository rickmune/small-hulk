using StructureMap.Configuration.DSL;
using TDR.WEB.LIB.Security;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Services.Clients.Impl;
using TDR.WEB.LIB.Services.Users;
using TDR.WEB.LIB.Services.Users.Impl;

namespace TDR.WEB.LIB.IOC
{
    public class WebRegistry : Registry
    {
        public WebRegistry()
        {
           
            For<IWebSecurityService>().Use<WebSecurityService>();
            For<IUserService>().Use<UserService>();
            For<IClientService>().Use<ClientService>();
        }
    }
}