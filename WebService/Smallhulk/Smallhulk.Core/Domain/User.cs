using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Smallhulk.Core.Domain
{
   public enum  UserType{Admin=1,User=2}
   public enum RegistrationType { Phone = 1, Email = 2 }

    public class User : BaseEntity
    {

        [Required]
        public string Username { get; set; }

        
        public string Password { get; set; }

        [Required]
        public string Fullname { get; set; }

        [Required]
        public UserType UserType { get; set; }

        public string Email { get; set; }

        public string PhoneNumber { get; set; }
        [Required]
        public RegistrationType RegistrationType { get; set; }


       

       

        [ForeignKey("AccountId")]
        public virtual Account Account { get; set; }

        public Guid AccountId { get; set; }


    }
}
