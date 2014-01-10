using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using TDR.Core.Data.EF;
using TDR.Core.Data.Services;
using TDR.Core.Domain.Forms;
using TDR.Core.Repository;
using TDR.Core.Util;

namespace TDR.Core.Data.Repository.Forms
{
    public class FormRepository : IFormRepository
    {
         protected TDRContext _context;
        public FormRepository(TDRContext context)
        {
            _context = context;
        }


        public FormQueryResult QueryForm(QueryBase query)
        {
            var q = query as QueryMasterData;
            var entityQuery = _context.Forms.AsQueryable();
            var queryResult = new FormQueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                entityQuery = entityQuery
                    .Where(s => s.Name.Contains(q.Name) );
            }
            if (q.Id.HasValue)
            {
                entityQuery = entityQuery
                    .Where(s => s.Id==q.Id);
            }
            queryResult.Count = entityQuery.Count();
            entityQuery = entityQuery.OrderBy(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                entityQuery = entityQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = entityQuery.ToList();
            return queryResult;
        }

        public RespondentTypeQueryResult QueryFormRespondentType(QueryBase query)
        {
            var q = query as QueryRespondentType;
            var entityQuery = _context.FormRespondentTypes.AsQueryable();
            var queryResult = new RespondentTypeQueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                entityQuery = entityQuery
                    .Where(s => s.Name.Contains(q.Name));
            }
            if (q.Id.HasValue)
            {
                entityQuery = entityQuery
                    .Where(s => s.Id == q.Id);
            }
            if (q.FormId.HasValue)
            {
                entityQuery = entityQuery.Where(s => s.FormId == q.FormId);
            }
            queryResult.Count = entityQuery.Count();
            entityQuery = entityQuery.OrderBy(s => s.Name);
            if (q.Skip.HasValue && q.Take.HasValue)
                entityQuery = entityQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = entityQuery.ToList();
            return queryResult;
        }

        public FormItemQueryResult QueryFormItem(QueryBase query)
        {
            var q = query as QueryFormItem;
            var entityQuery = _context.FormItems.AsQueryable();
            var queryResult = new FormItemQueryResult();
            if (!string.IsNullOrWhiteSpace(q.Name))
            {
                entityQuery = entityQuery
                    .Where(s => s.Label.Contains(q.Name));
            }
            if (q.Id.HasValue)
            {
                entityQuery = entityQuery
                    .Where(s => s.Id == q.Id);
            }
            if (q.FormId.HasValue)
            {
                entityQuery = entityQuery.Where(s => s.FormId == q.FormId);
            }
            queryResult.Count = entityQuery.Count();
            entityQuery = entityQuery.OrderBy(s => s.Order);
            if (q.Skip.HasValue && q.Take.HasValue)
                entityQuery = entityQuery.Skip(q.Skip.Value).Take(q.Take.Value);
            queryResult.Result = entityQuery.ToList();
            return queryResult;
        }

        public void SaveForm(DformEntity entity)
        {
            var form = _context.Forms.FirstOrDefault(s => s.Id == entity.Id);
            if (form == null)
            {
                form = new DformEntity
                {
                    Id = Guid.NewGuid(),
                };
                _context.Forms.Add(form);
            }
            form.ClientId = entity.ClientId;
            form.Name = entity.Name;
            form.IdCode = entity.IdCode;

            _context.SaveChanges();
           
        }

        public void SaveFormRespondentType(DformRespondentTypeEntity entity)
        {
            var respondentType = _context.FormRespondentTypes.FirstOrDefault(s => s.Id == entity.Id);
            if (respondentType == null)
            {
                respondentType = new DformRespondentTypeEntity
                {
                    Id = Guid.NewGuid(),

                };
                _context.FormRespondentTypes.Add(respondentType);
            }
            respondentType.Code = entity.Code;
            respondentType.Name = entity.Name;
            respondentType.FormId = entity.FormId;
            respondentType.IdCode = entity.IdCode;


            _context.SaveChanges();
        }

        public void SaveFormItem(DformItemEntity entity)
        {
            var item1 = _context.FormItems.FirstOrDefault(s => s.Id == entity.Id);
            if (item1 == null)
            {
                item1 = new DformItemEntity
                {
                    Id =entity.Id,
                };
                _context.FormItems.Add(item1);
            }
            item1.FormId = entity.FormId;
            item1.Label = entity.Label;
            item1.IsRequired = entity.IsRequired;
            item1.FormItemType = entity.FormItemType;
            item1.Order = entity.Order;
            item1.IdCode =entity.IdCode;
            item1.ValidationRegex = entity.ValidationRegex;
            item1.ValidationText = entity.ValidationText;
            item1.HelpText = entity.HelpText;
            item1.Section = entity.Section;
            //item1.SectionOrder = sectionorder;
            _context.SaveChanges();
           
        }

        public void SaveFormItemRespondent(DformItemRespondentTypeEntity entity)
        {
            var rItem = _context.FormItemRespondentTypes.FirstOrDefault(s => s.FormItemId == entity.FormItemId && s.FormRespondentTypeId ==entity.FormRespondentTypeId);
            if (rItem == null)
            {

                rItem =
                    new DformItemRespondentTypeEntity
                    {
                        Id = Guid.NewGuid(),
                    };
                _context.FormItemRespondentTypes.Add(rItem);
            }
            rItem.FormItemId = entity.FormItemId;
            rItem.FormRespondentTypeId = entity.FormRespondentTypeId;

            _context.SaveChanges();
        }

        public void DeleteFormItemRespondent(Guid itemid)
        {
            var rItem = _context.FormItemRespondentTypes.Where(s => s.FormItemId == itemid);
            foreach(var item in rItem)
            {
                _context.FormItemRespondentTypes.Remove(item);
            }
            _context.SaveChanges();
        }
    }
}
