using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

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

    }
}
