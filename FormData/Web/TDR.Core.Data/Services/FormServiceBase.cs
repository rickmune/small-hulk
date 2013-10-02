using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Data.EF;
using TDR.Core.Domain;
using TDR.Core.Domain.Forms;
using TDR.EF;

namespace TDR.Core.Data.Services
{
  public  class FormServiceBase

    {
      protected TDRContext _context;
      public FormServiceBase(TDRContext context)
      {
          _context = context;
      }
      protected void CreateAnswers(Guid itemId, string idcode, string text, string value)
      {
          var answer5 = _context.FormItemAnswers.FirstOrDefault(s => s.IdCode == idcode);
          if (answer5 == null)
          {
              answer5 = new DformItemAnswerEntity
                            {
                                Id = Guid.NewGuid(),
                               

                            };
               _context.FormItemAnswers.Add(answer5);
          }
          answer5.Text = text;
          answer5.Value = value;
          answer5.FormItemId = itemId;
          answer5.IdCode = idcode;
          _context.SaveChanges();

      }

      protected Guid CreateForm(string name, string idcode,Guid clientId)
      {
          var form = _context.Forms.FirstOrDefault(s => s.IdCode == idcode);
          if (form == null)
          {
              form = new DformEntity
                         {
                             Id = Guid.NewGuid(),
                         };
              _context.Forms.Add(form);
          }
          form.ClientId = clientId;
          form.Name = name;
          form.IdCode = idcode;
         
          _context.SaveChanges();
          return form.Id;
      }
      protected void CreateItemRespondentType(Guid itemId, Guid respondenttypeId)
      {

          var rItem = _context.FormItemRespondentTypes.FirstOrDefault(s => s.FormItemId == itemId && s.FormRespondentTypeId==respondenttypeId);
          if (rItem == null)
          {

              rItem =
                  new DformItemRespondentTypeEntity
                      {
                          Id = Guid.NewGuid(),
                      };
              _context.FormItemRespondentTypes.Add(rItem);
          }
          rItem.FormItemId = itemId;
          rItem.FormRespondentTypeId = respondenttypeId;
         
          _context.SaveChanges();

      }
      protected Guid CreateFormRespondentType(Guid formid, string idcode, string name, string code)
      {
          var respondentType = _context.FormRespondentTypes.FirstOrDefault(s => s.IdCode == idcode);
          if (respondentType == null)
           {
                respondentType = new DformRespondentTypeEntity
                                                              {
                                                                  Id = Guid.NewGuid(),
                                                                 
                                                              };
                _context.FormRespondentTypes.Add(respondentType);
           }
          respondentType.Code = code;
          respondentType.Name = name;
          respondentType.FormId = formid;
          respondentType.IdCode = idcode;
         

          _context.SaveChanges();
          return respondentType.Id;
      }

      protected Guid CreateFormItem(Guid formid, string idcode, string label, DformItemType type, int order, bool isRequired, string validationText = "", string validationRegex = "")
      {
          var item1 = _context.FormItems.FirstOrDefault(s => s.IdCode == idcode);
          if (item1 == null)
          {
              item1 = new DformItemEntity
                          {
                              Id = Guid.NewGuid(),
                          };
              _context.FormItems.Add(item1);
          }
          item1.FormId = formid;
          item1.Label = label;
          item1.IsRequired = isRequired;
          item1.FormItemType = type;
          item1.Order = order;
          item1.IdCode = idcode;
          item1.ValidationRegex = validationRegex;
          item1.ValidationText = validationText;
        
          _context.SaveChanges();
          return item1.Id;
      }
   
    }
}
