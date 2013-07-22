using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Data.EF;
using Smallhulk.Data.Repository;

namespace Smallhulk.Web.Controllers
{
   
    public class HomeController : Controller
    {
        //
        // GET: /Home/
        private IUserRepository _userRepository;

        public HomeController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }
         [Authorize(Roles = "Admin")]
        public ActionResult Index()
        {
           
          // var repo = new UserRepository(db);
            var query = new QueryMasterData {Name = "gitau"};
            var result = _userRepository.Query(query);
            if(result.Count==0)
            {
                SafAppDbContextInitializer initializer = new SafAppDbContextInitializer();
                initializer.Seed();
            }
            return View();
           
        }
         public ActionResult Register()
         {
             // SafAppDbContextInitializer initializer= new SafAppDbContextInitializer();
             // initializer.Seed();
             return View();

         }
        public ActionResult Seed()
        {
           // SafAppDbContextInitializer initializer= new SafAppDbContextInitializer();
           // initializer.Seed();
            return View("Index");

        }


    }
}
