using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Smallhulk.Data.EF;

namespace Smallhulk.Web.Controllers
{
    public class HomeController : Controller
    {
        //
        // GET: /Home/

        public ActionResult Index()
        {
            SafAppDbContext db = new SafAppDbContext();
           var user= db.Users.ToList();
            return View();
           
        }

    }
}
