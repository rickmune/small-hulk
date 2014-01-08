using System;
using System.Collections.Generic;
using Smallhulk.Core.Domain;
using TDR.Core.Domain.Forms;

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
    public class FormQueryResult
    {
        public FormQueryResult()
        {
            Result = new List<DformEntity>();
        }

        public List<DformEntity> Result { get; set; }
        public int Count { get; set; }

    }
    public class RespondentTypeQueryResult
    {
        public RespondentTypeQueryResult()
        {
            Result = new List<DformRespondentTypeEntity>();
        }

        public List<DformRespondentTypeEntity> Result { get; set; }
        public int Count { get; set; }

    }
    public class FormItemQueryResult
    {
        public FormItemQueryResult()
        {
            Result = new List<DformItemEntity>();
        }

        public List<DformItemEntity> Result { get; set; }
        public int Count { get; set; }

    }
    public abstract class QueryBase
    {
        public int? Skip { get; set; }
        public int? Take { get; set; }
        
    }
    public class QueryMasterData:QueryBase
    {
        public Guid? Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public Guid AccountId { get; set; }
    }
    public class QueryRespondentType : QueryMasterData
    {
        public Guid? FormId { get; set; }
        
    }
    public class QueryFormItem : QueryMasterData
    {
        public Guid? FormId { get; set; }

    }
    public class QueryReportItem : QueryBase
    {

        public string Name { get; set; }
        public string Description { get; set; }
        public Guid? GroupId { get; set; }
    }
    public class QueryReport : QueryBase
    {

        public string Name { get; set; }
        public string Description { get; set; }
        public Guid? ClientId { get; set; }
    }
}
