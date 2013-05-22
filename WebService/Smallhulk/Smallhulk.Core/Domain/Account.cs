using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
   public class Account:BaseEntity
    {
        [Required]
        public string Name { get; set; }

        
    }
}
