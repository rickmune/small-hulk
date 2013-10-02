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

using StructureMap;
using TDR.Core.Data.EF;
using TDR.Core.Data.IOC;
using TDR.Core.Repository;
using TDR.EF;
using TDR.WEB.LIB.IOC;
using TDR.WEB.LIB.Security;

namespace TDR
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
            Database.SetInitializer<TDRContext>(new DatabaseInitializer());
            InitIOC();
        }
        private void InitIOC()
        {
            //Database.SetInitializer<SafAppDbContext>(null);
            ObjectFactory.Initialize(x =>
            {
               
                x.AddRegistry<ServicesRegistry>();
                x.AddRegistry<RepositoryRegistry>();
                x.AddRegistry<WebRegistry>();
                

            });
            IContainer container = (IContainer)ObjectFactory.Container;
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
                    userInfo = new CachedUserInfo { UserId = user.Id, Username = this.User.Identity.Name, UserType = user.UserType, ClientId =user.ClientId.HasValue? user.ClientId.Value:Guid.Empty };
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