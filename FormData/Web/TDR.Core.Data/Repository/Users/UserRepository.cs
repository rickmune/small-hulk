using System;
using System.Linq;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.Users
{
    public class UserRepository : IUserRepository
    {
        private TDRContext _context;

        public UserRepository(TDRContext context)
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
                tbl.Password = entity.Password;
                tbl.Id = entity.Id;
                _context.Users.Add(tbl);
            }
            tbl.Username = entity.Username;

            tbl.UserType = entity.UserType;
            tbl.PhoneNumber = entity.PhoneNumber;
            tbl.Fullname = entity.Fullname;
            tbl.Email = entity.Email;
            tbl.UpdatedOn = date;
            if (entity.ClientId.HasValue)
                tbl.ClientId = entity.ClientId;
            if (entity.LocationId.HasValue)
                tbl.LocationId = entity.LocationId.Value;
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
                userQuery = userQuery
                    .Where(s => s.Username.Contains(q.Name) ||
                        s.Fullname.Contains(q.Description) || 
                        s.Email.Contains(q.Description)) ;
            }
           
            queryResult.Count = userQuery.Count();
            userQuery = userQuery.OrderBy(s => s.Username);
            if (q.Skip.HasValue && q.Take.HasValue)
                userQuery = userQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = userQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }

        public User Login(string username, string password)
        {
            return
                _context.Users.FirstOrDefault(s => s.Username.ToLower() == username.ToLower() && s.Password == password);
        }

        public User Get(string username)
        {
            return _context.Users.FirstOrDefault(s => s.Username.ToLower() == username.ToLower());
        }

        public bool CheckUserAvailability(string username)
        {
            return _context.Users.Any(s => s.Username.ToLower() == username.ToLower());
        }
    }
}
