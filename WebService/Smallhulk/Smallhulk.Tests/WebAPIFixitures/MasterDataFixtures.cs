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
            HttpClient client = Client();
            UserDTO user = new UserDTO()
            {
                Username = Guid.NewGuid().ToString(),
                Id = Guid.NewGuid(),
                IsActive = true,
                UserTypeId = 1,
                Email = "jgitau",
                AccountId = GetAccountId(),
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
           
            Guid categoryid = Guid.NewGuid();
            CategoryDTO dto = new CategoryDTO()
            {
                Name = Guid.NewGuid().ToString(),
                Id = categoryid,
                IsActive = true,
                AccountId = GetAccountId(),
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
                AccountId = GetAccountId(),
                CategoryId = categoryid,
                Description = "product web api",

            };
            var response2 = client.PostAsJsonAsync("api/phone/masterdata/addproduct", product);
            var _response2 = response2.Result;


        }
    }
}
