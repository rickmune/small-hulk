using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Web.Lib.DTOBuilders
{
    public  interface IDataTransferBuilder
    {
        TranferResponse<UserDTO> GetAllUsers();
        TranferResponse<UserDTO> Login(string username, string password);
       // TranferResponse GetAccount(Guid accountId);
        //BasicResponse AddUser(UserDTO user);
    }
     
    public class BasicResponse
    {
        
        public bool Status { get; set; }
        
        public string Info { get; set; }
    }

   
    public class TranferResponse<T> : BasicResponse
    {
        public TranferResponse()
        {
            Data = new List<T>();
        }

        public List<T> Data    ;
    }
}
