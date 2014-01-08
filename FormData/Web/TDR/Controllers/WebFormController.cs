using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcPaging;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Forms;
using TDR.WEB.LIB.DTOS.Users;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Services.Forms;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    [Authorize(Roles = "Admin")]
    public class WebFormController : Controller
    {
        //
        // GET: /Form/
        private IFormService _formService;
        private IClientService _clientService;

        public WebFormController(IFormService formService, IClientService clientService)
        {
            _formService = formService;
            _clientService = clientService;
        }

        public ActionResult Index(int? page)
        {
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryMasterData{ Skip = skip, Take = take };
            var result = _formService.Query(query);
            var item = result.Data;


            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);


            return View(data);
        }
        public ActionResult RespondentType(Guid formId, int? page)
        {
            ViewBag.FormId = formId;
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryRespondentType { Skip = skip, Take = take ,FormId = formId};
            var result = _formService.QueryRespondentType(query);
            var item = result.Data;
            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);

            return View(data);
        }
        public ActionResult FormItems(Guid formId, int? page)
        {
            ViewBag.FormId = formId;
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = 50;// WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryFormItem { Skip = skip, Take = take, FormId = formId };
            var result = _formService.QueryItem(query);
            var item = result.Data;
            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);

            return View(data);
        }
        public ActionResult CreateForm()
        {
            bind();
            return View();
        }
        [HttpPost]
        public ActionResult CreateForm(FormDTO model)
        {
            try
            {
                model.Id = Guid.NewGuid();
                //model.Password = Md5Hash.GetMd5Hash("1234");
                _formService.Save(model);

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

        public ActionResult EditForm(Guid id)
        {
            bind();
            var model = _formService.GetById(id);
            return View(model);
        }
        [HttpPost]
        public ActionResult EditForm(FormDTO model)
        {
            try
            {
               
                _formService.Save(model);

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
        public ActionResult EditFormRespondentType(Guid id)
        {
            bind();
            var model = _formService.GetRespondentTypeById(id);
            return View(model);
        }
        [HttpPost]
        public ActionResult EditFormRespondentType(FormRespondentTypeDTO model)
        {
            try
            {

                _formService.Save(model);

                return RedirectToAction("RespondentType",new {formId=model.FormId});
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
        private void bind()
        {

            ViewBag.ClientList =
                _clientService.Query(new QueryMasterData()).Data.Select(
                    s => new SelectListItem { Value = s.Id.ToString(), Text = s.Name });
        }
        public ActionResult CreateFormRepondentType(Guid formId)
        {
            
            var model = new FormRespondentTypeDTO {FormId = formId};
            return View(model);
        }
        [HttpPost]
        public ActionResult CreateFormRepondentType(FormRespondentTypeDTO model)
        {
            try
            {
                model.Id = Guid.NewGuid();
                //model.Password = Md5Hash.GetMd5Hash("1234");
                _formService.Save(model);

                return RedirectToAction("RespondentType",new {formId=model.FormId});
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
