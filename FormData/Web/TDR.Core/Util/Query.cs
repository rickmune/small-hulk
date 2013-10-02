using System;
using System.Collections.Generic;
using Smallhulk.Core.Domain;

namespace TDR.Core.Util
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
        public Guid AccountId { get; set; }
    }
}
