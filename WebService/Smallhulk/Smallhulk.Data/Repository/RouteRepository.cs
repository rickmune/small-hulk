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
    public class RouteRepository : IRouteRepository
    {
        private SafAppDbContext _context;

        public RouteRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Route itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Route entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Route  Details not Valid");
            DateTime date = DateTime.Now;
            Route tbl = _context.Routes.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Route();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.Routes.Add(tbl);
            }
            tbl.Name = entity.Name;
            tbl.UpdatedOn = date;
            tbl.Code = entity.Code;
            tbl.Account = entity.Account;
            tbl.AccountId = entity.AccountId;
            _context.SaveChanges();
        }

        public Route GetById(Guid id)
        {
            return _context.Routes.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var accountQuery = _context.Routes.AsQueryable();
            var q = query as QueryMasterData;
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                accountQuery = accountQuery.Where(s => s.Name.Contains(q.Name));
            }
            queryResult.Count = accountQuery.Count();
            accountQuery = accountQuery.OrderByDescending(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                accountQuery = accountQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = accountQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
}
