using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.UI.WebControls;
using MvcPaging;
using TDR.Core.Domain.Clients;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.DTOS.Users;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Services.Locations;
using TDR.WEB.LIB.Services.Users;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    [Authorize(Roles = "Admin")]
    public class UserController : Controller
    {
        //
        // GET: /Client/

        private IUserService _userService;
        private IClientService _clientService;
        public ILocationService _locationService;

        public UserController(IUserService userService, IClientService clientService, ILocationService locationService)
        {
            _userService = userService;
            _clientService = clientService;
            _locationService = locationService;
        }

        public ActionResult Index(int? page)
        {
           
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip =currentPageIndex * take;
            var query = new QueryMasterData {Skip = skip, Take = take};
            var result = _userService.Query(query);
            var item = result.Data;
           // int? total = result.RecordCount == take ? take+1 : result.RecordCount;
            int? total = result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);
            
            
            return View(data);
        }
        public ActionResult Create()
        {
            bind();
            return View();
        }

        private void bind()
        {
            ViewBag.ClientList =
                _clientService.Query(new QueryMasterData()).Data.Select(
                    s => new SelectListItem {Value = s.Id.ToString(), Text = s.Name});
            ViewBag.LocationList =
               _locationService.Query(new QueryMasterData()).Data.Select(
                   s => new SelectListItem { Value = s.Id.ToString(), Text = s.Name });
            ViewBag.UserTypeList = new List<SelectListItem>
                                       {
                                           new SelectListItem {Value = ((int) UserType.Admin).ToString(), Text = "Admin"},
                                           new SelectListItem {Value = ((int) UserType.TDR).ToString(), Text = "TDR"}
                                       };
        }

        [HttpPost]
        public ActionResult Create(UserDTO model)
        {
            try
            {
                if(_userService.CheckAvailablity(model.Username))
                {
                    ModelState.AddModelError("", "Username already taken ");
                    bind();
                    return View();
                }
                if (model.UserTypeId==(int)UserType.TDR && !model.LocationId.HasValue)
                {
                    ModelState.AddModelError("", "Location is required for User type TDR ");
                    bind();
                    return View();
                }
                model.Id = Guid.NewGuid();
                model.Password = Md5Hash.GetMd5Hash("1234");
                _userService.Save(model);
               
                return RedirectToAction("Index");
            }
            catch (DomainValidationException ve)
            {
                ve.DomainValidationErrors(ModelState);
                bind();
                return View();
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);
                bind();
                return View();
            }
            return View();
        }
        public ActionResult Edit(Guid id)
        {
            bind();
            var model = _userService.GetById(id);
            return View(model);
        }
        [HttpPost]
        public ActionResult Edit(UserDTO model)
        {
            try
            {
               
                if (model.UserTypeId == (int)UserType.TDR && !model.LocationId.HasValue)
                {
                    ModelState.AddModelError("", "Location is required for User type TDR ");
                    bind();
                    return View();
                }
               
                _userService.Save(model);

                return RedirectToAction("Index");
            }
            catch (DomainValidationException ve)
            {
                ve.DomainValidationErrors(ModelState);
                bind();
                return View();
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);
                bind();
                return View();
            }
            return View();
        }
        public ActionResult Create2()
        {
            return View();
        }

    }
}
