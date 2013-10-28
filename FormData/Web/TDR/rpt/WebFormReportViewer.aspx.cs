using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Security.Principal;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Microsoft.Reporting.WebForms;
using StructureMap;
using TDR.Core.Domain.Settings;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.rpt
{
    public partial class WebFormReportViewer : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        protected void Page_Init(object sender, EventArgs e)
        {
            var configRepository = ObjectFactory.GetInstance<IConfigRepository>();
            string cachedKey = string.Format("cui_{0}", this.User.Identity.Name);
            //CachedUserInfo userInfo = HttpContext.Current.Cache[cachedKey] as CachedUserInfo;
            string uri = Request.QueryString["reporturi"];
            string server = "";
            var conserver = configRepository.Load(ConfigType.ReportServer);
            if (conserver != null)
                server = conserver.Value;

            ReportViewer1.ServerReport.ReportServerUrl = new Uri(server);
            ReportViewer1.ProcessingMode = ProcessingMode.Remote;
            ReportViewer1.ServerReport.ReportPath = uri;
            ReportViewer1.ServerReport.ReportServerCredentials= new ReportServerCredentials();
           

            ReportViewer1.ServerReport.Refresh();
        }
    }

    public class ReportServerCredentials : IReportServerCredentials
    {
        public ReportServerCredentials()
        {
        }
        public WindowsIdentity ImpersonationUser
        {
            get
            {
                return null;
            }
        }
        public ICredentials NetworkCredentials
        {
            get
            {
                var configRepository = ObjectFactory.GetInstance<IConfigRepository>();
                string userename = "";
                string userpassword = "";
              
                var conusername = configRepository.Load(ConfigType.ReportServerUsername);
                if (conusername != null)
                    userename = conusername.Value;
                var conuserpassword = configRepository.Load(ConfigType.ReportServerPassword);
                if (conuserpassword != null)
                    userpassword = IcoderEncryption.DecryptString(conuserpassword.Value);
                return new NetworkCredential(userename,userpassword);
            }
        }
        public bool GetFormsCredentials(out Cookie authCookie, out string user, out string password, out string authority)
        {
           
            authCookie = null;
            user = "";// userename;// ConfigurationManager.AppSettings["USERNAME"];
            password = ""; //userpassword;// ConfigurationManager.AppSettings["PASSWORD"];
            authority = "";// ConfigurationManager.AppSettings["SERVER_NAME"];
            return false;
        }
    }
}