using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
    public class Route : BaseEntity
    {
        [ForeignKey("AccountId")]
        public Account Account { get; set; }
        [Required]
        public Guid AccountId { get; set; }
        [Required]
        public String Name { get; set; }
        public String Code { get; set; }
    }
}
