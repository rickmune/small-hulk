using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcPaging;
using TDR.Core.Domain.Clients;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.BI;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.Services.BI;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    [Authorize(Roles = "Admin")]
    public class ReportGroupItemController : Controller
    {
        //
        // GET: /Client/

        private IReportGroupService _reportGroupService;
        private IReportGroupItemService _reportGroupItemService;

        public ReportGroupItemController(IReportGroupService reportGroupService, IReportGroupItemService reportGroupItemService)
        {
            _reportGroupService = reportGroupService;
            _reportGroupItemService = reportGroupItemService;
        }

        public ActionResult Index(int? page)
        {
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryReportItem { Skip = skip, Take = take };
            var result = _reportGroupItemService.Query(query);
            var item = result.Data;


            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);


            return View(data);
        }
        private void bind()
        {
            ViewBag.GroupList = _reportGroupService.Query(new QueryReport()).Data.Select(
                s => new SelectListItem {Value = s.Id.ToString(), Text = s.Name});
        }


        public ActionResult Create()
        {
            bind();

            return View();
        }
        [HttpPost]
        public ActionResult Create(ReportGroupItemDTO model)
        {
            try
            {

                model.Id = Guid.NewGuid();
                _reportGroupItemService.Save(model);

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
            var model = _reportGroupItemService.GetById(id);

            return View(model);
        }
        [HttpPost]
        public ActionResult Edit(ReportGroupItemDTO model)
        {
            try
            {


                _reportGroupItemService.Save(model);

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
