using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace Smallhulk.Web
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
            config.Routes.MapHttpRoute(
                name: "GetUsers",
                routeTemplate: "api/phone/masterdata/users",
                defaults: new { controller = "MasterData", action = "GetAllUsers" }
                );
            config.Routes.MapHttpRoute(
               name: "Login",
               routeTemplate: "api/phone/masterdata/login",
               defaults: new { controller = "MasterData", action = "login" }
               );
            config.Routes.MapHttpRoute(
              name: "GetCountries",
              routeTemplate: "api/phone/masterdata/countries",
              defaults: new { controller = "MasterData", action = "GetCountries" }
              );
            config.Routes.MapHttpRoute(
             name: "AddAccount",
             routeTemplate: "api/phone/masterdata/addaccount",
             defaults: new { controller = "MasterData", action = "AddAccount" }
             );
            config.Routes.MapHttpRoute(
             name: "AddUser",
             routeTemplate: "api/phone/masterdata/adduser",
             defaults: new { controller = "MasterData", action = "AddUser" }
             );
            config.Routes.MapHttpRoute(
            name: "AddCategory",
            routeTemplate: "api/phone/masterdata/addcategory",
            defaults: new { controller = "MasterData", action = "AddCategory" }
            );
            config.Routes.MapHttpRoute(
           name: "AddProduct",
           routeTemplate: "api/phone/masterdata/addproduct",
           defaults: new { controller = "MasterData", action = "AddProduct" }
           );
            //config.EnableSystemDiagnosticsTracing();
        }
    }
}
