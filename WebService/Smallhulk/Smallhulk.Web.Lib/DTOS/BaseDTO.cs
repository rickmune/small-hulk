using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Web.Lib.DTOS
{
   public abstract  class BaseDTO
    {
       public Guid Id { get; set; }
       public bool IsActive { get; set; }
    }
}
