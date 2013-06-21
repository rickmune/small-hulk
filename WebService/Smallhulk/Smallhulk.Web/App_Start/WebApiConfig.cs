﻿using System;
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
                defaults: new {id = RouteParameter.Optional}
                );
            config.Routes.MapHttpRoute(
                name: "GetUsers",
                routeTemplate: "api/phone/masterdata/users",
                defaults: new {controller = "MasterData", action = "GetAllUsers"}
                );
            config.Routes.MapHttpRoute(
                name: "Login",
                routeTemplate: "api/phone/masterdata/login/{username}/{password}",
                defaults: new { controller = "MasterData", action = "login", username = "username", password = "password" }
                );
            config.Routes.MapHttpRoute(
                name: "GetCountries",
                routeTemplate: "api/phone/masterdata/countries",
                defaults: new {controller = "MasterData", action = "GetCountries"}
                );
            config.Routes.MapHttpRoute(
                name: "AddAccount",
                routeTemplate: "api/phone/masterdata/addaccount",
                defaults: new {controller = "MasterData", action = "AddAccount"}
                );
            config.Routes.MapHttpRoute(
                name: "AddUser",
                routeTemplate: "api/phone/masterdata/adduser",
                defaults: new {controller = "MasterData", action = "AddUser"}
                );
            config.Routes.MapHttpRoute(
                name: "AddCategory",
                routeTemplate: "api/phone/masterdata/addcategory",
                defaults: new {controller = "MasterData", action = "AddCategory"}
                );
            config.Routes.MapHttpRoute(
                name: "AddProduct",
                routeTemplate: "api/phone/masterdata/addproduct",
                defaults: new {controller = "MasterData", action = "AddProduct"}
                );
            config.Routes.MapHttpRoute(
                name: "GetAccount",
                routeTemplate: "api/phone/masterdata/account/{accountid}",
                defaults: new { controller = "MasterData", action = "GetAccount", accountid = "accountid" }
                );
           
            config.Routes.MapHttpRoute(
               name: "GetCategories",
               routeTemplate: "api/phone/masterdata/categories/{accountid}",
               defaults: new { controller = "MasterData", action = "GetCategories", accountid = "accountid" }
               );
            config.Routes.MapHttpRoute(
              name: "GetProducts",
              routeTemplate: "api/phone/masterdata/products/{accountid}",
              defaults: new { controller = "MasterData", action = "GetProducts", accountid = "accountid" }
              );
            config.Routes.MapHttpRoute(
             name: "GetRoutes",
             routeTemplate: "api/phone/masterdata/routes/{accountid}",
             defaults: new { controller = "MasterData", action = "GetRoutes", accountid = "accountid" }
             );
            config.Routes.MapHttpRoute(
             name: "GetOutlets",
             routeTemplate: "api/phone/masterdata/outlets/{accountid}",
             defaults: new { controller = "MasterData", action = "GetOutlets", accountid = "accountid" }
             );
            config.Routes.MapHttpRoute(
                name: "AddRoute",
                routeTemplate: "api/phone/masterdata/addroute",
                defaults: new { controller = "MasterData", action = "AddRoute" }
                );
            config.Routes.MapHttpRoute(
                name: "AddOutlet",
                routeTemplate: "api/phone/masterdata/addoutlet",
                defaults: new { controller = "MasterData", action = "AddOutlet" }
                );
            //config.EnableSystemDiagnosticsTracing();
        }
    }
}
