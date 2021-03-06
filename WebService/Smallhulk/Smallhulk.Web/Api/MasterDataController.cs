﻿using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web.Http;
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
             int take;
             int skip;
              var parameters = this.Request.RequestUri.ParseQueryString();
             string search = parameters["search"];
             PagingParam(out take,out skip);
             var query = new QueryMasterData();
             if(take!=0)
             {
                 query.Skip = skip;
                 query.Take = take;
             }
             if(!string.IsNullOrWhiteSpace(search))
             {
                 query.Name = search;
                 query.Description = search;
                 //query.Skip = 0;
             }

             var all = _dataTransferBuilder.GetAllUsers(query);
            return all;
        }

         private void PagingParam(out int take, out int skip)
         {

             int page = 0;
             var parameters = this.Request.RequestUri.ParseQueryString();
             Int32.TryParse(parameters["page"], out page);
             Int32.TryParse(parameters["per_page"], out take);
            
             skip = (page-1)*take;
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
        public BasicResponse Register(RegisterDTO register)
        {
            var all = _dataTransferBuilder.Register(register);
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
        [HttpGet]
        public TranferResponse<AccountDTO> GetAccount(Guid accountid)
        {
            var all = _dataTransferBuilder.GetAccount(accountid);
            return all;
        }
        [HttpGet]
        public TranferResponse<CategoryDTO> GetCategories(Guid accountid)
        {
            int take;
            int skip;
            var parameters = this.Request.RequestUri.ParseQueryString();
            string search = parameters["search"];
            PagingParam(out take, out skip);
            var query = new QueryMasterData();
            query.AccountId = accountid;
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

            var all = _dataTransferBuilder.GetCategory(query);
            return all;
        }
        [HttpGet]
        public TranferResponse<ProductDTO> GetProducts(Guid accountid)
        {
            var all = _dataTransferBuilder.GetProduct(accountid);
            return all;
        }
        [HttpGet]
        public TranferResponse<RouteDTO> GetRoutes(Guid accountid)
        {
            var all = _dataTransferBuilder.GetRoute(accountid);
            return all;
        }
        [HttpGet]
        public TranferResponse<OutletDTO> GetOutlets(Guid accountid)
        {
            var all = _dataTransferBuilder.GetOutlet(accountid);
            return all;
        }
        [HttpPost]
        public BasicResponse AddRoute(RouteDTO dto)
        {
            var all = _dataTransferBuilder.AddRoute(dto);
            return all;
        }
        [HttpPost]
        public BasicResponse AddOutlet(OutletDTO dto)
        {
            var all = _dataTransferBuilder.AddOutlet(dto);
            return all;
        }
    }
}
