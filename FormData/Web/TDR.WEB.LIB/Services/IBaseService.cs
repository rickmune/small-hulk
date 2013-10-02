using System;
using TDR.Core.Util;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services
{
    public interface IBaseService<T> where T : class
    {
        BasicResponse Save(T dto);
        T GetById(Guid id);
        TranferResponse<T> Query(QueryBase query);
    }
}