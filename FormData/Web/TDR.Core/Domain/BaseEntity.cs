using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
   public class BaseEntity
    {
       [Required]
       public Guid Id { get; set; }
        [Required]
       public DateTime CreatedOn { get; set; }
       public DateTime UpdatedOn { get; set; }
        [Required]
       public bool IsActive { get; set; }

    }
}
