using System;
using System.ComponentModel.DataAnnotations;
using TDR.Core.Domain.Clients;

namespace TDR.WEB.LIB.DTOS.BI
{
    public class ReportGroupDTO : BaseDTO
    {
       
        [Required]
        public string Name { get; set; }
        
        public string Description { get; set; }
         [Required]
        public Guid ClientId { get; set; }
        public string ClientName { get; set; }
       
       
    }
    public class ReportGroupItemDTO : BaseDTO
    {
         [Required]
        public string Name { get; set; }
        public string Description { get; set; }
         [Required]
        public string ReportUrl { get; set; }
         [Required]
        public int ReportOrder { get; set; }
         [Required]
        public Guid GroupId { get; set; }
        public string GroupName  { get; set; }


    }
}
