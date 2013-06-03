using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using NUnit.Framework;
using Smallhulk.Web.Lib.DTOBuilders;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Tests.WebAPIFixitures
{
    [TestFixture]
    class MasterDataFixtures : BaseWebApi
    {
        [Test]
        public void AddAccount()
        {
            string urlSuffix = "api/phone/masterdata/addaccount";
            HttpClient client = Client();

            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            Guid accountId = Guid.NewGuid();
            AccountDTO dto = new AccountDTO()
                                 {
                                     Name = Guid.NewGuid().ToString(),
                                     Id = accountId,
                                     IsActive = true,
                                 };
            var response = client.PostAsJsonAsync("api/phone/masterdata/addaccount", dto);
            var _response = response.Result;
            UserDTO user = new UserDTO()
            {
                Username = Guid.NewGuid().ToString(),
                Id = accountId,
                IsActive = true,
                UserTypeId = 1,
                Email = "jgitau",
                AccountId = accountId,
                Fullname = "James Mathai",
                Password = Guid.NewGuid().ToString(),
                PhoneNumber = "0122222222",
                
            };
            var response2 = client.PostAsJsonAsync("api/phone/masterdata/adduser", user);
            var _response2 = response2.Result;


        }
        [Test]
        public void AddProduct()
        {
            //string urlSuffix = "api/phone/masterdata/addcategory";
            HttpClient client = Client();

            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            Guid accountId = Guid.NewGuid();
            AccountDTO dtoacount = new AccountDTO()
            {
                Name = Guid.NewGuid().ToString(),
                Id = accountId,
                IsActive = true,
            };
            var responseacc = client.PostAsJsonAsync("api/phone/masterdata/addaccount", dtoacount);
            var _responseacc = responseacc.Result;
            Guid categoryid = Guid.NewGuid();
            CategoryDTO dto = new CategoryDTO()
            {
                Name = Guid.NewGuid().ToString(),
                Id = categoryid,
                IsActive = true,
                AccountId = accountId,
                Description = "noma",

            };
            var response = client.PostAsJsonAsync("api/phone/masterdata/addcategory", dto);
            var _response = response.Result;
            
            ProductDTO product = new ProductDTO()
            {
                Name = Guid.NewGuid().ToString(),
                Id = Guid.NewGuid(),
                IsActive = true,
                BuyingPrice = 1,
                SellingPrice = 90,
                AccountId = accountId,
                CategoryId = categoryid,
                Description = "product web api",

            };
            var response2 = client.PostAsJsonAsync("api/phone/masterdata/addproduct", product);
            var _response2 = response2.Result;


        }
    }
}
