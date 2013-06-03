using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Web.Lib.DTOBuilders;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Web.Api
{

    public class MasterDataController : ApiController
    {
        private IDataTransferBuilder _dataTransferBuilder;

        public MasterDataController(IDataTransferBuilder dataTransferBuilder)
        {
            _dataTransferBuilder = dataTransferBuilder;
        }
         [HttpGet]
        public TranferResponse<UserDTO> GetAllUsers()
        {
            var all = _dataTransferBuilder.GetAllUsers();
            return all;
        }
        [HttpGet]
         public HttpResponseMessage Login(string username, string password)
        {
            HttpStatusCode returnCode = HttpStatusCode.OK;

            var response = new TranferResponse<UserDTO>();
            try
            {
                response = _dataTransferBuilder.Login(username, password);
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                returnCode = HttpStatusCode.ServiceUnavailable;
            }
            return Request.CreateResponse(returnCode, response);
            
        }
        [HttpGet]
        public TranferResponse<CountryDTO> GetCountries()
        {
            var all = _dataTransferBuilder.GetCountry();
            return all;
        }
        [HttpPost]
        public BasicResponse AddAccount(AccountDTO account)
        {
            var all = _dataTransferBuilder.AddAccount(account);
            return all;
        }
        [HttpPost]
        public BasicResponse AddUser(UserDTO user)
        {
            var all = _dataTransferBuilder.AddUser(user);
            return all;
        }
        [HttpPost]
        public BasicResponse AddCategory(CategoryDTO dto)
        {
            var all = _dataTransferBuilder.AddCategory(dto);
            return all;
        }
        [HttpPost]
        public BasicResponse AddProduct(ProductDTO dto)
        {
            var all = _dataTransferBuilder.AddProduct(dto);
            return all;
        }
    }
}
