using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;

namespace Smallhulk.Web.Lib.DTOS
{
    
    public class UserDTO : BaseDTO
    {

        public string Username { get; set; }
        public string Password { get; set; }
        public string Fullname { get; set; }
        public int UserTypeId { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public Guid AccountId { get; set; }
    }
    public class RegisterDTO : BaseDTO
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string Fullname { get; set; }
        public int UserTypeId { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public Guid AccountId { get; set; }
    }
}
