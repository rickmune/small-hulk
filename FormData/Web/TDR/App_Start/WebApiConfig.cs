using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace TDR
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
                name: "GetForm",
                routeTemplate: "api/client/form/getform/{formId}",
                defaults: new {controller = "Form", action = "GetForm", formId = "formId"}
                );
            config.Routes.MapHttpRoute(
                name: "GetFormIds",
                routeTemplate: "api/client/form/getformids/{clientId}",
                defaults: new {controller = "Form", action = "GetFormIds", clientId = "clientId"}
                );
            config.Routes.MapHttpRoute(
                name: "PublishFormResult",
                routeTemplate: "api/client/form/publish",
                defaults: new {controller = "Form", action = "PublishFormResult"}
                );
             config.Routes.MapHttpRoute(
                name: "GetFormRespodent",
                routeTemplate: "api/client/form/GetFormRespodent",
                defaults: new { controller = "Form", action = "GetFormRespodent" }
                );
             config.Routes.MapHttpRoute(
                name: "SaveFormItem",
                routeTemplate: "api/client/form/SaveFormItem",
                defaults: new { controller = "Form", action = "SaveFormItem" }
                );

             config.Routes.MapHttpRoute(
                name: "GetFormItem",
                routeTemplate: "api/client/form/GetFormItem",
                defaults: new { controller = "Form", action = "GetFormItem" }
                );
            //users
            config.Routes.MapHttpRoute(
                name: "Authenticate",
                routeTemplate: "api/client/user/login/{username}/{password}",
                defaults: new {controller = "User", action = "Login", username = "username", password = "password"}
                );
            config.Routes.MapHttpRoute(
                name: "GetUsers",
                routeTemplate: "api/client/user/users",
                defaults: new {controller = "User", action = "Users"}
                );
            config.Routes.MapHttpRoute(
                name: "checkuseravailabilty",
                routeTemplate: "api/client/user/checkuseravailabilty",
                defaults: new {controller = "User", action = "CheckUserAvailabilty"}
                );
            config.Routes.MapHttpRoute(
                name: "AddUser",
                routeTemplate: "api/client/user/save",
                defaults: new {controller = "User", action = "Save"}
                );

            config.Routes.MapHttpRoute(
               name: "Report1",
               routeTemplate: "api/client/dashboard/outletstatus",
               defaults: new { controller = "Dashboard", action = "Index" }
               );
        }
    }
}
