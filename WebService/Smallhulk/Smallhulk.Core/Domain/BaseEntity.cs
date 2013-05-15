using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
   public class BaseEntity
    {
       public Guid Id { get; set; }
       public DateTime CreatedOn { get; set; }
       public DateTime UpdatedOn { get; set; }
       public bool IsActive { get; set; }

    }
}
