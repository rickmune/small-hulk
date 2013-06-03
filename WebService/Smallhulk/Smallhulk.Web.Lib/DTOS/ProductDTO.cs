using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Web.Lib.DTOS
{
    public class ProductDTO : BaseDTO
    {

        public string Name { get; set; }

        public string Description { get; set; }

        public Guid CategoryId { get; set; }

        public Guid AccountId { get; set; }

        public decimal BuyingPrice { get; set; }

        public decimal SellingPrice { get; set; }
    }
}
