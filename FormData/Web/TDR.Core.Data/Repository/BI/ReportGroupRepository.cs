using System;
using System.Linq;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Domain.BI;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.BI
{
    public class ReportGroupRepository : IReportGroupRepository
    {
        private TDRContext _context;

        public ReportGroupRepository(TDRContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(ReportGroup itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(ReportGroup entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "ReportGroups  Details not Valid");
            DateTime date = DateTime.Now;
            ReportGroup tbl = _context.ReportGroups.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new ReportGroup();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.ReportGroups.Add(tbl);
            }
            tbl.Name = entity.Name;

            tbl.Description = entity.Description;
            tbl.ClientId = entity.ClientId;
            tbl.UpdatedOn = date;
            _context.SaveChanges();
        }

        public ReportGroup GetById(Guid id)
        {
            return _context.ReportGroups.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var q = query as QueryReport;
            var myQuery = _context.ReportGroups.AsQueryable();
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                myQuery = myQuery
                    .Where(s => s.Name.Contains(q.Name) ||
                        s.Description.Contains(q.Description) );
            }
            if(q.ClientId.HasValue)
            {
                myQuery = myQuery.Where(s => s.ClientId == q.ClientId);
            }
            queryResult.Count = myQuery.Count();
            myQuery = myQuery.OrderBy(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                myQuery = myQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = myQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
}
