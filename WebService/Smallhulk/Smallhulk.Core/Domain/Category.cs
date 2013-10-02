using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
   public class Category:BaseEntity
    {
       [Required]
       public string Name { get; set; }
      
       public string Description { get; set; }

       [ForeignKey("AccountId")]
       public virtual Account Account { get; set; }
       [Required]
       public Guid AccountId { get; set; }

     
    }
}
