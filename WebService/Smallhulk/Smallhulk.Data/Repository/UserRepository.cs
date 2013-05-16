using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Data.EF;

namespace Smallhulk.Data.Repository
{
    public class UserRepository : IUserRepository
    {
        private SafAppDbContext _context;

        public UserRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(User itemToValidate)
        {
            return itemToValidate.BasicValidation();
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
