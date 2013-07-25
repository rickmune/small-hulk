using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using Newtonsoft.Json.Linq;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Web.Lib.DTOBuilders;
using Smallhulk.Web.Lib.Security;

namespace Smallhulk.Web.Api
{
    public class UserController : ApiController
    {
        //
        // GET: /User/
        private IUserRepository _userRepository;
        private IDataTransferBuilder _mapper;

        public UserController(IUserRepository userRepository, IDataTransferBuilder dataTransferBuilder)
        {
            _userRepository = userRepository;
            _mapper = dataTransferBuilder;
        }

        [System.Web.Http.HttpGet]
        public HttpResponseMessage Index()
        {
            var data = _userRepository.Query(new QueryMasterData()).Result.OfType<User>().Select(s=>_mapper.Map(s)).ToList();
            return Request.CreateResponse(HttpStatusCode.OK, data);
        }
        [System.Web.Http.HttpGet]
        public HttpResponseMessage Authenticate()
        {
            string cachedKey = string.Format("cui_{0}", this.User.Identity.Name);
            CachedUserInfo userInfo = HttpContext.Current.Cache[cachedKey] as CachedUserInfo;
            if (userInfo != null)
                return Request.CreateResponse(HttpStatusCode.OK, userInfo);
            else
                return Request.CreateResponse(HttpStatusCode.Forbidden);
        }
       
        public HttpResponseMessage CheckUserAvailabilty(JObject data)
        {
            string username = (string) data["username"];
            return Request.CreateResponse(HttpStatusCode.OK, _userRepository.CheckUserAvailability(username));

        }

    }
}
