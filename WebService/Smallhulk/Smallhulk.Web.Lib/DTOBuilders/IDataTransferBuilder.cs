using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Web.Lib.DTOBuilders
{
    public  interface IDataTransferBuilder
    {
        IEnumerable<UserDTO> GetAllUsers();
        UserDTO Login(string username,string password);
    }
}
