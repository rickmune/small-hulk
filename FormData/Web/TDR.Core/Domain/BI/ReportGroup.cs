using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Clients;

namespace TDR.Core.Domain.BI
{
    [Table("tblReportGroup")]
   public class ReportGroup : BaseEntity
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public Guid ClientId { get; set; }
        public virtual Client Client { get; set; }
        public virtual ICollection<ReportGroupItem> Reports { set; get; }
    }
    [Table("tblReportItem")]
    public class ReportGroupItem : BaseEntity
    {

        public string Name { get; set; }
        public string Description { get; set; }
        public string ReportUrl { get; set; }
        public int ReportOrder { get; set; }
        public Guid GroupId { get; set; }
        public virtual ReportGroup Group { get; set; }

    }
}
