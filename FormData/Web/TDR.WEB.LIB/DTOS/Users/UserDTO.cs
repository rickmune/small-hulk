using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.WEB.LIB.DTOS.Users
{
    public class UserDTO : BaseDTO
    {
        [Required]
        public string Username { get; set; }
        public string Password { get; set; }
         [Required]
        public string Fullname { get; set; }
         [Required]
        public int UserTypeId { get; set; }
        public string UserType { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        [Required]
        public Guid? ClientId { get; set; }
        public string ClientName { get; set; }
        public Guid? LocationId { get; set; }
        public string LocationName { get; set; }
       
    }
}
