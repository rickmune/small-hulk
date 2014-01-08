using Smallhulk.Core.Domain;
using TDR.Core.Domain.Users;

namespace TDR.Core.Repository
{
   public  interface  IUserRepository:IRepository<User>
   {
       User Login(string username, string password);

       User Get(string name);
       bool CheckUserAvailability(string username);
       User MobileLogin(string username, string password);
   }
}
