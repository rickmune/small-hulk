using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
    public class Outlet : BaseEntity
    {
        [ForeignKey("AccountId")]
        public Account Account { get; set; }
        [Required]
        public Guid AccountId { get; set; }
         [ForeignKey("RouteId")]
        public Route Route { get; set; }
         [Required]
         public Guid RouteId { get; set; }
        [Required]
        public String Name { get; set; }
        public string Code { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
    }
}
