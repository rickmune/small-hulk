using System.Security.Principal;
using TDR.WEB.LIB.Security;

namespace TDR.Security
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
