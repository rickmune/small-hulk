using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using TDR.Core.Util;
using TDR.WEB.LIB.Services.BI;

namespace TDR.Controllers
{
    public class ReportMenu
    {
        public  ReportMenu()
        {
            Item= new List<ReportMenuItem>();
        }
        public string Name { get; set; }
       public List<ReportMenuItem> Item { get; set; }

    }

    public class ReportMenuItem
    {
        public string Name { get; set; }
        public string Uri { get; set; }


    }
[Authorize(Roles = "Admin")]
    public class ReportMenuController : Controller
    {
        private IReportGroupService _reportGroupService;
        private IReportGroupItemService _reportGroupItemService;

        public ReportMenuController(IReportGroupService reportGroupService, IReportGroupItemService reportGroupItemService)
        {
            _reportGroupService = reportGroupService;
            _reportGroupItemService = reportGroupItemService;
        }


        public ActionResult Index()
        {
            var groups = _reportGroupService.Query(new QueryReport());
            var list = new List<ReportMenu>();
            foreach (var group in groups.Data)
            {
                ReportMenu menu = new ReportMenu();
                menu.Name = group.Name;
                var groupItems = _reportGroupItemService.Query(new QueryReportItem{GroupId = group.Id});
                foreach (var groupItem in groupItems.Data)
                {
                    ReportMenuItem menuItem = new ReportMenuItem();
                    menuItem.Name = groupItem.Name;
                    menuItem.Uri = groupItem.ReportUrl;
                    menu.Item.Add(menuItem);

                }
                list.Add(menu);
            }
            return View(list);
        }
        public ActionResult Navigation(string reporturi,string reportname)
        {
            @ViewBag.reporturi = reporturi;
            ViewBag.reportname = reportname;
            return View();
        }
    }
}
