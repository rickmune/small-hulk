using System.Collections.Specialized;
using System.Security.Principal;

namespace Smallhulk.Web.Lib.Security
{
    public class CustomIdentity : IIdentity
    {
        private bool _bIsAuthenticated;
        private string _sAuthenticationType;
        private string _sUserName;
        private StringCollection _lstRoles = new StringCollection();


        public CustomIdentity(string sUserName, bool bIsAuthenticated, string sAuthenticationType)
        {
            _sUserName = sUserName;
            _bIsAuthenticated = bIsAuthenticated;
            _sAuthenticationType = sAuthenticationType;
        }

        public string AuthenticationType    //Required by IIdentity
        {
            get { return _sAuthenticationType; }
        }

        public bool IsAuthenticated    //Required by IIdentity
        {
            get { return _bIsAuthenticated; }
        }

        public string Name    //Required by IIdentity
        {
            get { return _sUserName; }
        }

        public StringCollection Roles    //For the ‘role’ elements.
        {
            get { return _lstRoles; }
        }
    }
}
