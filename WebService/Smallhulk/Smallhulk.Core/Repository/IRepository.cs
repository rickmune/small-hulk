using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Util;

namespace Smallhulk.Core.Repository
{
    public interface IRepository<T> : IValidation<T> where T : class 
    {
        void Save(T entity);
        T GetById(Guid id);
    }
}
