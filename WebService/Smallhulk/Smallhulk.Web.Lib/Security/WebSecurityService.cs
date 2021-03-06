﻿using System.Security.Principal;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;

namespace Smallhulk.Web.Lib.Security
{
    public class WebSecurityService : IWebSecurityService
    {
        private IUserRepository _userRepository;
       

        public WebSecurityService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
           
        }
        public bool AuthenticateUser(string userName, string password)
        {
            User hquser = _userRepository.Login(userName, password);
            if (hquser != null)
                return true;
            return false;
        }

        public CustomPrincipal ConstructCustomPrincipal(IIdentity identity, CachedUserInfo userInfo)
        {
            if (userInfo == null)
                return null;
            var ci = new CustomIdentity(userInfo.Username, identity.IsAuthenticated, identity.AuthenticationType);
            switch (userInfo.UserType)
            {
                case UserType.Admin:
                    ci.Roles.Add("Admin");
                    ci.Roles.Add("User");
                    break;
                case UserType.User:
                    ci.Roles.Add("User");
                    break;
            }
            return new CustomPrincipal(ci, userInfo);
        }
    }
}
