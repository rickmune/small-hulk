using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using TDR.Core.Util;
using TDR.Security;
using TDR.WEB.LIB.Security;

namespace Smallhulk.Web.Controllers
{
    public class AccountController : Controller
    {
        //
        // GET: /Account/
        private IWebSecurityService _webSecurityService;

        public AccountController(IWebSecurityService webSecurityService)
        {
            _webSecurityService = webSecurityService;
        }

        public ActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Login(string username, string password, string returnUrl)
        {
            if (string.IsNullOrWhiteSpace(username))
                ModelState.AddModelError("username", "Username is required");
            if (string.IsNullOrWhiteSpace(password))
                ModelState.AddModelError("password", "Password is required");
            if (!ModelState.IsValid)
                return View();
            bool success = _webSecurityService.AuthenticateUser(username, Md5Hash.GetMd5Hash(password));

            if (!success)
                ModelState.AddModelError("", "Failed to login");
            if (!ModelState.IsValid)
                return View();
            if (string.IsNullOrEmpty(returnUrl))
            {
                //if (this.User.IsInRole("Admin"))
                returnUrl = Url.Action("index", "home");
                //if (this.User.IsInRole("Supervisor"))
                //    returnUrl = Url.Action("index", "Supervisor");

            }

            FormsAuthentication.SetAuthCookie(username, true);
            return Redirect(returnUrl);
        }
        public ActionResult Logout()
        {
            FormsAuthentication.SignOut();
            return RedirectToAction("Login");
        }
    }
}
