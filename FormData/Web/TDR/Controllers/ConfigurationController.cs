using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using TDR.Core.Domain.Settings;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Configurations;
using TDR.WEB.LIB.Util;

namespace TDR.Controllers
{
    public class ConfigurationController : Controller
    {
        //
        // GET: /Configuration/
        private IConfigRepository _configRepository;

        public ConfigurationController(IConfigRepository configRepository)
        {
            _configRepository = configRepository;
        }

        public ActionResult ReportServer()
        {
            var con = new  ReportServerConfigDTO();
            var conServer = _configRepository.Load(ConfigType.ReportServer);
            if (conServer != null)
                con.ReportServerUri = conServer.Value;
            var conServerusername = _configRepository.Load(ConfigType.ReportServerUsername);
            if (conServerusername != null)
                con.ReportServerUsername = conServerusername.Value;
            var conServerpassword = _configRepository.Load(ConfigType.ReportServerPassword);
            if (conServerpassword != null)
                con.ReportServerPassword =IcoderEncryption.DecryptString( conServerpassword.Value);
            return View(con);
        }
        [HttpPost]
        public ActionResult ReportServer(ReportServerConfigDTO model)
        {
            try
            {

                var conServer = new Config
                                    {
                                        Description = model.ReportServerUri,
                                        Value = model.ReportServerUri,
                                        ConfigType = ConfigType.ReportServer
                                    };
                _configRepository.Save(conServer);
                var conUsername = new Config
                                      {
                                          Description = model.ReportServerUsername,
                                          Value = model.ReportServerUsername,
                                          ConfigType = ConfigType.ReportServerUsername
                                      };
                _configRepository.Save(conUsername);
                var conPassword = new Config
                                      {
                                          Description = "",
                                          Value = IcoderEncryption.EncryptString(model.ReportServerPassword),
                                          ConfigType = ConfigType.ReportServerPassword
                                      };
                _configRepository.Save(conPassword);
                ModelState.AddModelError("","Report server settings saved successfully");
                return View(model);
            }
            catch (DomainValidationException ve)
            {
                ve.DomainValidationErrors(ModelState);

                return View(model);
            }
            catch (Exception ex)
            {
                ModelState.AddModelError("", ex.Message);

                return View(model);
            }


        }

    }
}
