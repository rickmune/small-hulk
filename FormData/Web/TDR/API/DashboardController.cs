using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Bortosky.Google.Visualization;
using TDR.Core.Data.EF;
using TDR.EF;
using TDR.Models;

namespace TDR.API
{
    public class DashboardController : ApiController
    {
        //
        // GET: /Dashboard/
         [System.Web.Http.HttpGet]
        public string Index()
        {
            List<ActiveOutlet> list = new List<ActiveOutlet>();
            TDRContext db = new TDRContext();
            var statuses = new List<string> {"Active", "Closed", "Suspended", "Relocated", "MIT"};
            foreach (var resp in db.FormRespondentTypes.Where(s => s.Form.IdCode == "SAF_D_D_F" & s.Name == "M-PESA").ToList())
            {
                foreach (var status in statuses)
                {
                    var active = new ActiveOutlet();
                    active.RespondentType = resp.Name;
                    active.Status = status;
                    active.Count = db.FormResultItem.Count(s => s.FormItem.Order == 4 && s.FormItemAnswer == status && s.FormResult.RespondentTypeId==resp.Id);
                    list.Add(active);
                }


            }
             var data=list.ToList().ToDataTable();
             var data2 = PivotHelper.Pivot(data, "RespondentType", "Status", "Count");
             return new GoogleDataTable(data2).GetJson();
        }

    }
}
