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
    public class CategoryRepository : ICategoryRepository
    {
        private SafAppDbContext _context;

        public CategoryRepository(SafAppDbContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Category itemToValidate)
        {
          return itemToValidate.BasicValidation();
        }

        public void Save(Category entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Category  Details not Valid");
            DateTime date = DateTime.Now;
            Category tbl = _context.Categories.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Category();
                tbl.CreatedOn = date;
                tbl.IsActive = true;

                tbl.Id = entity.Id;
                _context.Categories.Add(tbl);
            }
            tbl.Name = entity.Name;
            tbl.AccountId = entity.AccountId;
            tbl.UpdatedOn = date;
            tbl.Description = entity.Description;
            _context.SaveChanges();
        }

        public Category GetById(Guid id)
        {
            return _context.Categories.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
         // var da=  _context.Database.SqlQuery<TestClass>("Select Id,Name from Categories");
            var userQuery = _context.Categories.AsQueryable();
            var q = query as QueryMasterData;
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                userQuery = userQuery.Where(s => s.Name.Contains(q.Name));
            }
            if (q.AccountId!=Guid.Empty)
            {
                userQuery = userQuery.Where(s => s.AccountId==q.AccountId);
            }
            queryResult.Count = userQuery.Count();
            userQuery = userQuery.OrderByDescending(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                userQuery = userQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = userQuery.ToList().OfType<BaseEntity>().ToList();
            return queryResult;
        }
    }
    public class  TestClass
    {
        public string Name { get; set; }
        public Guid Id { get; set; }
        
    }
}
