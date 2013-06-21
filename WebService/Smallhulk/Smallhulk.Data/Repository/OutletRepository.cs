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
    public class OutletRepository : IOutletRepository
    {
        private SafAppDbContext _context;

        public OutletRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Outlet itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Outlet entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Outlet  Details not Valid");
            DateTime date = DateTime.Now;
            Outlet tbl = _context.Outlets.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Outlet();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.Outlets.Add(tbl);
            }
            tbl.Name = entity.Name;
            tbl.UpdatedOn = date;
            tbl.Code = entity.Code;
            tbl.Account = entity.Account;
            tbl.AccountId = entity.AccountId;
            tbl.RouteId = entity.RouteId;
            tbl.Route = entity.Route;
            tbl.Longitude = entity.Longitude;
            tbl.Latitude = entity.Latitude;
            _context.SaveChanges();
        }

        public Outlet GetById(Guid id)
        {
            return _context.Outlets.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var outletQuery = _context.Outlets.AsQueryable();
            var q = query as QueryMasterData;
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                outletQuery = outletQuery.Where(s => s.Name.Contains(q.Name));
            }
            queryResult.Count = outletQuery.Count();
            outletQuery = outletQuery.OrderByDescending(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                outletQuery = outletQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = outletQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
}
