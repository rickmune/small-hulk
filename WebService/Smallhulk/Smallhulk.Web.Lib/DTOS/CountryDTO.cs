using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Web.Lib.DTOS
{
    public class CountryDTO : BaseDTO
    {
        public string Code { get; set; }
        public string ZipCode { get; set; }
        public string Name { get; set; }
    }
}
