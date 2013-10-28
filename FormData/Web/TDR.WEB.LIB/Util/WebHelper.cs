using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Mvc;
using TDR.Core.Util;

namespace TDR.WEB.LIB.Util
{
    public static class WebHelper
    {
        public const int DefaultPageSize = 10;

        public static string FormatException(this DomainValidationException ex)
        {
            string info = ex.Message.ToUpper();
            foreach (var validationResult in ex.ValidationResult.Results)
            {
                info += "\n\t" + validationResult.ErrorMessage;
            }
            return info;
        }

        public static void PagingParam(this ApiController api, out int take, out int skip)
        {

            int page = 0;
            var parameters = api.Request.RequestUri.ParseQueryString();
            Int32.TryParse(parameters["page"], out page);
            Int32.TryParse(parameters["per_page"], out take);

            skip = (page - 1)*take;
        }

        public static void DomainValidationErrors(this DomainValidationException ve, ModelStateDictionary modelState)
        {

            modelState.AddModelError("", ve.Message);
            ValidationResultInfo vri = ve.ValidationResult;
            List<ValidationResult> vrs = vri.Results;
            foreach (ValidationResult vr in vrs)
            {

                modelState.AddModelError("", vr.ErrorMessage);
            }

        }


        public static void DomainValidationErrors(this  ModelStateDictionary modelState ,string dve)
        {
            modelState.AddModelError("", dve);
        }

        public static MvcHtmlString MyResource(this HtmlHelper helper, string resource)
        {
            string authority = helper.ViewContext.HttpContext.Request.Url.Authority;
            string scheme = helper.ViewContext.HttpContext.Request.Url.Scheme;
            var urlHelper = new UrlHelper(helper.ViewContext.RequestContext);
            string webroot = urlHelper.Content("~");
            string urlformat = string.Format("{0}://{1}{2}{3}", scheme, authority, webroot, resource);
            return MvcHtmlString.Create(urlformat);
        }

    }
}
