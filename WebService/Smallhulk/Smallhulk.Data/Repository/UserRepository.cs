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
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "user  Details not Valid");
            DateTime date = DateTime.Now;
            User tbl = _context.Users.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new User();
                tbl.CreatedOn = date;
                tbl.IsActive = true;

                tbl.Id = entity.Id;
                _context.Users.Add(tbl);
            }
            tbl.Username = entity.Username;
            tbl.Password = entity.Password;
            tbl.CountryId = entity.Country.Id;
            tbl.UserType = entity.UserType;
            tbl.PhoneNumber = entity.PhoneNumber;
            tbl.Fullname = entity.Fullname;
            tbl.Email = entity.Email;tbl.UpdatedOn = date;

            _context.SaveChanges();
           
          
        }

        public User GetById(Guid id)
        {
            return _context.Users.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var q = query as QueryMasterData;
            var userQuery = _context.Users.AsQueryable();
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                userQuery = userQuery.Where(s => s.Username.Contains(q.Name));
            }
            queryResult.Count = userQuery.Count();
            userQuery = userQuery.OrderByDescending(s => s.Username);
            if (q.Skip.HasValue && q.Take.HasValue)
                userQuery = userQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = userQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }

        
    }
}
