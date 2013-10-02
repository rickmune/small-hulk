using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Users;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Users
{
    public interface IUserService : IBaseService<UserDTO>
    {
        
        TranferResponse<UserDTO> Login(string username, string password);
        bool CheckAvailablity(string username);
    }
}
