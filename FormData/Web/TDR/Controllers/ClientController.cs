using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcPaging;
using TDR.Core.Domain.Clients;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.Services.Clients;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    public class ClientController : Controller
    {
        //
        // GET: /Client/

        private IClientService _clientService;

        public ClientController(IClientService clientService)
        {
            _clientService = clientService;
        }

        public ActionResult Index(int? page)
        {
            int currentPageIndex = page.HasValue ? page.Value - 1 : 0;
            int take = WebHelper.DefaultPageSize;
            int skip = currentPageIndex * take;
            var query = new QueryMasterData {Skip = skip, Take = take};
            var result = _clientService.Query(query);
            var item = result.Data;


            int? total = result.RecordCount < take ? take : result.RecordCount;
            var data = item.ToPagedList(currentPageIndex, take, total);
            
            
            return View(data);
        }

    }
}
