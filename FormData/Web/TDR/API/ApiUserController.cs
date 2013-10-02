using System;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Newtonsoft.Json.Linq;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Users;
using TDR.WEB.LIB.Security;
using TDR.WEB.LIB.Services.Users;
using TDR.WEB.LIB.Util;

namespace TDR.API
{
    public class UserController : ApiController
    {
        //
        // GET: /User/
        private IUserService _userService;


        public UserController(IUserService userService)
        {
            _userService = userService;
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
        // [System.Web.Http.HttpPost]
        //public HttpResponseMessage CheckUserAvailabilty(JObject data)
        //{
        //    string username = (string)data["username"];
        //     string userId = (string) data["Id"];
        //     Guid id = Guid.Empty;
        //     Guid.TryParse(userId, out id);
        //    return Request.CreateResponse(HttpStatusCode.OK, _userService.CheckUserAvailability(username,id));

        //}
        [HttpGet]
        public HttpResponseMessage Login(string username, string password)
        {
            HttpStatusCode returnCode = HttpStatusCode.OK;

            var response = new TranferResponse<UserDTO>();
            try
            {
                response = _userService.Login(username, password);
                response.Status = true;
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                response.Status = false;
                returnCode = HttpStatusCode.ServiceUnavailable;
            }
            return Request.CreateResponse(returnCode, response);

        }
        [System.Web.Http.HttpGet]
        public TranferResponse<UserDTO> Users()
        {
            int take;
            int skip;
            var parameters = this.Request.RequestUri.ParseQueryString();
            string search = parameters["search"];
            this.PagingParam(out take, out skip);
            var query = new QueryMasterData();

            if (take != 0)
            {
                query.Skip = skip;
                query.Take = take;
            }
            if (!string.IsNullOrWhiteSpace(search))
            {
                query.Name = search;
                query.Description = search;
                //query.Skip = 0;
            }

            var all = _userService.Query(query);
            return all;
        }

        [System.Web.Http.HttpPost]
        public BasicResponse Save(UserDTO dto)
        {
            var response = _userService.Save(dto);
            return response;
        }

    }
}
