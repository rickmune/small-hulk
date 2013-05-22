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
    public class AccountRepository : IAccountRepository
    {
        private SafAppDbContext _context;

        public AccountRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Account itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Account entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Account  Details not Valid");
            DateTime date = DateTime.Now;
            Account tbl = _context.Accounts.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Account();
                tbl.CreatedOn = date;
                tbl.IsActive = true;

                tbl.Id = entity.Id;
                _context.Accounts.Add(tbl);
            }
            tbl.Name = entity.Name ;
            
            
            tbl.UpdatedOn = date;
           


            _context.SaveChanges();
        }

        public Account GetById(Guid id)
        {
            return _context.Accounts.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var userQuery = _context.Accounts.AsQueryable();
            var q = query as QueryMasterData;
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                userQuery = userQuery.Where(s => s.Name.Contains(q.Name));
            }
            queryResult.Count = userQuery.Count();
            userQuery = userQuery.OrderByDescending(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                userQuery = userQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = userQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
}
