using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Web.Lib.DTOS
{
   public class CategoryDTO : BaseDTO
    {
       
        public string Name { get; set; }
       public string Description { get; set; }
        public Guid AccountId { get; set; }
    }
}
