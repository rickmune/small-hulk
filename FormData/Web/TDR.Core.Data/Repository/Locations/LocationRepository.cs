using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Domain.Clients;

using TDR.Core.Domain.Locations;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.Locations
{
    public class LocationRepository : ILocationRepository
    {

        private TDRContext _context;

        public LocationRepository(TDRContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Location itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Location entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Location  Details not Valid");
            DateTime date = DateTime.Now;
            Location tbl = _context.Locations.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Location();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.Locations.Add(tbl);
            }
            tbl.Name = entity.Name;

            tbl.Description = entity.Description;
            tbl.StructureId = entity.StructureId;
            tbl.Code = entity.Code;
            tbl.ParentId = entity.ParentId ?? Guid.Empty;

            tbl.UpdatedOn = date;


            _context.SaveChanges();
        }

        public Location GetById(Guid id)
        {
            return _context.Locations.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var q = query as QueryMasterData;
            var userQuery = _context.Locations.AsQueryable();
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                userQuery = userQuery
                    .Where(s => s.Name.Contains(q.Name) ||
                        s.Description.Contains(q.Description));
            }

            queryResult.Count = userQuery.Count();
            userQuery = userQuery.OrderBy(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                userQuery = userQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = userQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
}
