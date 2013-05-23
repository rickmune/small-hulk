using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;

namespace Smallhulk.Core.Util
{
    public class QueryResult
    {
        public QueryResult()
        {
            Result = new List<BaseEntity>();
        }

        public List<BaseEntity> Result { get; set; }
        public int Count { get; set; }

    }
    public abstract class QueryBase
    {
        public int? Skip { get; set; }
        public int? Take { get; set; }
        
    }
    public class QueryMasterData:QueryBase
    {
        
        public string Name { get; set; }
        public string Description { get; set; }
    }
}
