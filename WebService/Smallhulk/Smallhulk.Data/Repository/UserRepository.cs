using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;

namespace Smallhulk.Data.Repository
{
    public class UserRepository : IUserRepository
    {
        public ValidationResultInfo Validate(User itemToValidate)
        {
            throw new NotImplementedException();
        }

        public void Save(User entity)
        {
            throw new NotImplementedException();
        }

        public User GetById(Guid id)
        {
            throw new NotImplementedException();
        }
    }
}
