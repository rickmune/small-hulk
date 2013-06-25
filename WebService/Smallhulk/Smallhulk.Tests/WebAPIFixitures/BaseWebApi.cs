using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Tests
{
   public class BaseWebApi
    {
       protected HttpClient Client()
       {
           HttpClient client = new HttpClient();
           client.BaseAddress = new Uri("http://10.0.0.193:49445/");
           return client;
       }

       private static Guid AccountNo { set; get; }

       protected  Guid GetAccountId ()
       {
           if ( AccountNo == Guid.Empty)
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
               AccountNo = accountId;
           
           }
           
           return AccountNo;
       }
    }
}
