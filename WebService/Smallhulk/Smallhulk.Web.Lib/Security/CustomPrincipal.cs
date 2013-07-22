using System.Security.Principal;

namespace Smallhulk.Web.Lib.Security
{
    public class CustomPrincipal : IPrincipal
    {
        private CustomIdentity _cidIdentity;

        public CustomPrincipal(CustomIdentity cidIdentity, CachedUserInfo userInfo)
        {
            _cidIdentity = cidIdentity;
            UserInfo = userInfo;
        }

        public IIdentity Identity    //Required by IPrincipal
        {
            get { return _cidIdentity; }
        }

        public bool IsInRole(string sRole)    //Required by IPrincipal
        {
            return _cidIdentity.Roles.Contains(sRole);
        }

        public CachedUserInfo UserInfo
        {
            get; private set; }
    }
}
