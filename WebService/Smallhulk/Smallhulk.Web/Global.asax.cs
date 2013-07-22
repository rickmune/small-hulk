using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Security.Principal;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Data.EF;
using Smallhulk.Data.IOC;
using Smallhulk.Web.Lib;
using Smallhulk.Web.Lib.IOC;
using Smallhulk.Web.Lib.Security;
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
            WebApiConfig.RegisterMainApp(GlobalConfiguration.Configuration);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
           
           // Database.SetInitializer<SafAppDbContext>(new SafAppDbContextInitializer());
            IntializeIOC();

        }
         private void IntializeIOC()
         {
             //Database.SetInitializer<SafAppDbContext>(null);
             ObjectFactory.Initialize(x =>
                                          {
                                              x.AddRegistry<RepositoryRegistry>();
                                              x.AddRegistry<BuilderRegistry>();

                                          });
             IContainer container = (IContainer) ObjectFactory.Container;
             DependencyResolver.SetResolver(new StructureMapDependencyResolver(container));
             GlobalConfiguration.Configuration.DependencyResolver = new StructureMapResolver(container);
         }
         protected void Application_AuthenticateRequest(object sender, EventArgs e)
         {
             if (this.User != null)
             {
                 string cachedKey = string.Format("cui_{0}", this.User.Identity.Name);
                 CachedUserInfo userInfo = HttpContext.Current.Cache[cachedKey] as CachedUserInfo;
                 if (userInfo == null)
                 {

                     var userRepository = DependencyResolver.Current.GetService<IUserRepository>();
                     var user = userRepository.Get(this.User.Identity.Name);
                     if (user == null)
                         return;
                     userInfo = new CachedUserInfo
                                    {UserId = user.Id, Username = this.User.Identity.Name, UserType = user.UserType,AccountId=user.AccountId};
                     //hquser 

                 }
                 HttpContext.Current.Cache.Insert(cachedKey, userInfo);
                 var ws = DependencyResolver.Current.GetService<IWebSecurityService>();
                 IPrincipal cp = ws.ConstructCustomPrincipal(this.User.Identity, userInfo);
                 this.Context.User = cp;
             }
         }



    }
}