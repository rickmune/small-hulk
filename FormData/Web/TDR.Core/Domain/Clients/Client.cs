using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Forms;
using TDR.Core.Domain.Users;

namespace TDR.Core.Domain.Clients
{

    [Table("tblClient")]
    public class Client : BaseEntity
    {

        [Required]
        public string Name { get; set; }
        [Required]
        public string Code { get; set; }
        public virtual ICollection<User> Users { set; get; }
        public virtual ICollection<DformEntity> Forms { set; get; }
        

    }
}
