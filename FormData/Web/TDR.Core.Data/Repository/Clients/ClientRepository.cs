using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Domain.Clients;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.Clients
{
    public class ClientRepository : IClientRepository
    {
        private TDRContext _context;

        public ClientRepository(TDRContext context)
        {
            _context = context;
        }

        public ValidationResultInfo Validate(Client itemToValidate)
        {
            return itemToValidate.BasicValidation();
        }

        public void Save(Client entity)
        {
            ValidationResultInfo vri = Validate(entity);
            if (!vri.IsValid)
                throw new DomainValidationException(vri, "Client  Details not Valid");
            DateTime date = DateTime.Now;
            Client tbl = _context.Clients.FirstOrDefault(s => s.Id == entity.Id);
            if (tbl == null)
            {
                tbl = new Client();
                tbl.CreatedOn = date;
                tbl.IsActive = true;
                tbl.Id = entity.Id;
                _context.Clients.Add(tbl);
            }
            tbl.Name = entity.Name;

            tbl.Code = entity.Code;
           
            
            tbl.UpdatedOn = date;
          

            _context.SaveChanges();
        }

        public Client GetById(Guid id)
        {
            return _context.Clients.FirstOrDefault(s => s.Id == id);
        }

        public QueryResult Query(QueryBase query)
        {
            var q = query as QueryMasterData;
            var userQuery = _context.Clients.AsQueryable();
            var queryResult = new QueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                userQuery = userQuery
                    .Where(s => s.Name.Contains(q.Name) ||
                        s.Code.Contains(q.Description) );
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
