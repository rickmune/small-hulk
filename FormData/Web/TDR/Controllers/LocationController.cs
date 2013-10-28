using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcPaging;
using TDR.Core.Domain.Clients;

using TDR.Core.Domain.Locations;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.DTOS.Locations;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Services.Locations;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    [Authorize(Roles = "Admin")]
    public class LocationController : Controller
    {
        //
        // GET: /Client/

        private ILocationService _locationService;

        public LocationController(ILocationService locationService)
        {
            _locationService = locationService;
        }

        public ActionResult Index(int? page)
        {
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryMasterData {Skip = skip, Take = take};
            var result = _locationService.Query(query);
            var item = result.Data;


            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);
            
            
            return View(data);
        }
        private void bind()
        {
           
            ViewBag.LocationStructureList = new List<SelectListItem>
                                       {
                                           new SelectListItem {Value = ((int) LocationStructure.Region).ToString(), Text = "Region"},
                                           
                                       };
        }


        public ActionResult Create()
        {
            bind();
           
            return View();
        }
        [HttpPost]
        public ActionResult Create(LocationDTO model)
        {
            try
            {
               
                model.Id = Guid.NewGuid();
                _locationService.Save(model);

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
            var model = _locationService.GetById(id);

            return View(model);
        }
        [HttpPost]
        public ActionResult Edit(LocationDTO model)
        {
            try
            {

                
                _locationService.Save(model);

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
    }
}
