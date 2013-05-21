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

        public ActionResult Index()
        {
           

          // var repo = new UserRepository(db);
            var query = new QueryMasterData {Name = "gitau"};
            var result = _userRepository.Query(query);
            return View();
           
        }

    }
}
