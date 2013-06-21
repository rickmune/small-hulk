using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Web.Lib.DTOS
{
    public class OutletDTO : BaseDTO
    {
        public Guid AccountId { get; set; }
        public Guid RouteId { get; set; }
        public string Name { get; set; }
        public string Code { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
    }
}
