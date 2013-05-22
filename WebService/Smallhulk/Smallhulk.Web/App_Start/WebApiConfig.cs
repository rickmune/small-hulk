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
            //config.EnableSystemDiagnosticsTracing();
        }
    }
}
