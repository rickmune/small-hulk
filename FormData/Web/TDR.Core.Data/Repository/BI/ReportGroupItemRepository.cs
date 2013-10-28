using System;
using System.Linq;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Domain.BI;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.BI
{
    public class ReportGroupItemRepository : IReportGroupItemRepository
    {
        private TDRContext _context;

        public ReportGroupItemRepository(TDRContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(ReportGroupItem itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(ReportGroupItem entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "ReportGroupItem  Details not Valid");
            DateTime date = DateTime.Now;
            ReportGroupItem tbl = _context.ReportGroupItems.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new ReportGroupItem();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.ReportGroupItems.Add(tbl);
            }
            tbl.Name = entity.Name;

            tbl.Description = entity.Description;
            tbl.GroupId = entity.GroupId;
            tbl.ReportOrder = entity.ReportOrder;
            tbl.ReportUrl = entity.ReportUrl;

            tbl.UpdatedOn = date;
            _context.SaveChanges();
        }

        public ReportGroupItem GetById(Guid id)
        {
            return _context.ReportGroupItems.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var q = query as QueryReportItem;
            var myQuery = _context.ReportGroupItems.AsQueryable();
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                myQuery = myQuery
                    .Where(s => s.Name.Contains(q.Name) ||
                        s.Description.Contains(q.Description) );
            }
            if (q.GroupId.HasValue)
            {
                myQuery = myQuery.Where(s => s.GroupId == q.GroupId);
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
