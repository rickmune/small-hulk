using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using Smallhulk.Data.EF;
using Smallhulk.Data.IOC;
using Smallhulk.Web.Lib;
using Smallhulk.Web.Lib.IOC;
using StructureMap;

namespace Smallhulk.Web
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            WebApiConfig.Register(GlobalConfiguration.Configuration);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
           
           // Database.SetInitializer<SafAppDbContext>(new SafAppDbContextInitializer());
            IntializeIOC();

        }
         private void IntializeIOC()
         {
             ObjectFactory.Initialize(x =>
                                          {
                                              x.AddRegistry<RepositoryRegistry>();
                                              x.AddRegistry<BuilderRegistry>();

                                          });
             IContainer container = (IContainer) ObjectFactory.Container;
             DependencyResolver.SetResolver(new StructureMapDependencyResolver(container));
             GlobalConfiguration.Configuration.DependencyResolver = new StructureMapResolver(container);
         }
    }
}