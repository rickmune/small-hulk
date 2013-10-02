using System;
using TDR.Core.Util;

namespace TDR.Core.Repository
{
    public interface IRepository<T> : IValidation<T> where T : class 
    {
        void Save(T entity);
        T GetById(Guid id);
        QueryResult Query(QueryBase query);
    }
}
