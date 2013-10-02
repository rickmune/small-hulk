using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TDR.WEB.LIB.Util
{
    public class BasicResponse
    {
       
       
        public bool Status { get; set; }

        public string Info { get; set; }
    }


    public class TranferResponse<T> : BasicResponse
    {
        public TranferResponse()
        {
            Data = new List<T>();
        }

        public int RecordCount { get; set; }
        public List<T> Data;
    }
}
