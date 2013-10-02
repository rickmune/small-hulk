using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Clients;

namespace TDR.Core.Domain.Users
{
   public enum  UserType{Admin=1,TDR=2}

    [Table("tblUser")]
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
       
        public virtual Client Client { get; set; }

        public Guid? ClientId { get; set; }


    }
}
