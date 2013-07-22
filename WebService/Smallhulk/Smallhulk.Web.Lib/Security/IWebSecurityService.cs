using System;
using System.Security.Principal;
using Smallhulk.Core.Domain;

namespace Smallhulk.Web.Lib.Security
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
        public Guid AccountId { get; set; }
       
        
    }
}
