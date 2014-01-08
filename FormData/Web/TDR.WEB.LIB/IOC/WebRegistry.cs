using StructureMap.Configuration.DSL;
using TDR.WEB.LIB.Security;
using TDR.WEB.LIB.Services.BI;
using TDR.WEB.LIB.Services.BI.Impl;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Services.Clients.Impl;
using TDR.WEB.LIB.Services.Forms;
using TDR.WEB.LIB.Services.Forms.Impl;
using TDR.WEB.LIB.Services.Locations;
using TDR.WEB.LIB.Services.Locations.Impl;
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
            For<ILocationService>().Use<LocationService>();
            For<IReportGroupItemService>().Use<ReportGroupItemService>();
            For<IReportGroupService>().Use<ReportGroupService>();
            For<IFormService>().Use<FormService>();
          
        }
    }
}