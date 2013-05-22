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
    public class ProductRepository : IProductRepository
    {
        private SafAppDbContext _context;

        public ProductRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Product itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Product entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Product  Details not Valid");
            DateTime date = DateTime.Now;
            Product tbl = _context.Products.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Product();
                tbl.CreatedOn = date;
                tbl.IsActive = true;

                tbl.Id = entity.Id;
                _context.Products.Add(tbl);
            }
            tbl.Name = entity.Name;
            tbl.AccountId = entity.AccountId;
            tbl.UpdatedOn = date;
            tbl.CategoryId = entity.CategoryId;
            tbl.Description = entity.Description;
            tbl.BuyingPrice = entity.BuyingPrice;
            tbl.SellingPrice = entity.SellingPrice;
            _context.SaveChanges();
        }

        public Product GetById(Guid id)
        {
            return _context.Products.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var userQuery = _context.Products.AsQueryable();
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
