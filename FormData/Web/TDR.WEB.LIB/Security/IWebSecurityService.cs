using System;
using System.Security.Principal;
using TDR.Core.Domain.Users;
using TDR.Security;

namespace TDR.WEB.LIB.Security
{
    public interface IWebSecurityService
    {
        bool AuthenticateUser(string userName, string password);
        CustomPrincipal ConstructCustomPrincipal(IIdentity identity, CachedUserInfo userInfo);
    }

    public class CachedUserInfo
    {
        public Guid UserId { get; set; }
        public string Username { get; set; }
        public UserType UserType { get; set; }
        public Guid ClientId { get; set; }
       
        
    }
}
