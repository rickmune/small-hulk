using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;

namespace Smallhulk.Core.Repository
{
   public  interface  IUserRepository:IRepository<User>
   {
       User Login(string username, string password);

       User Get(string name);
       bool CheckUserAvailability(string username);
   }
}
