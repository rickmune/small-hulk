using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Domain;
using TDR.Core.Domain.Forms;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Forms;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Forms.Impl
{
    public class FormService : IFormService
    {
        private IFormRepository _formRepository;

        public FormService(IFormRepository formRepository)
        {
            _formRepository = formRepository;
        }

        public void Save(FormDTO dto)
        {
            var entity= new DformEntity();
            entity.Id = dto.Id;
            entity.ClientId = dto.ClientId;
            entity.IdCode = dto.Code;
            entity.Name = dto.Name;
            _formRepository.SaveForm(entity);

        }

        public void Save(FormRespondentTypeDTO dto)
        {
            var entity = new DformRespondentTypeEntity();
            entity.Id = dto.Id;
            entity.FormId = dto.FormId;
            entity.IdCode = dto.Code;
            entity.Name = dto.Name;
            entity.Code = dto.Value;
            
            _formRepository.SaveFormRespondentType(entity);
        }

        public FormDTO GetById(Guid id)
        {
            return _formRepository.QueryForm(new QueryMasterData {Id = id}).Result.Select(Map).FirstOrDefault();
        }

        public FormRespondentTypeDTO GetRespondentTypeById(Guid id)
        {
            return _formRepository.QueryFormRespondentType(new QueryRespondentType { Id = id }).Result.Select(Map).FirstOrDefault();
        }

        public FormItemDTO GetItemById(Guid id)
        {
            return _formRepository.QueryFormItem(new QueryFormItem { Id = id }).Result.Select(Map).FirstOrDefault();
        }

        private FormDTO Map(DformEntity s)
        {
            return new FormDTO
            {
                Id = s.Id,
                IsActive =true,
                Name = s.Name,
                Code = s.IdCode,
                ClientId = s.ClientId,
                ClientName = s.Client.Name,
               
            };
        }
        private FormRespondentTypeDTO Map(DformRespondentTypeEntity s)
        {
            return new FormRespondentTypeDTO
            {
                Id = s.Id,
                IsActive = true,
                Name = s.Name,
                Code = s.IdCode,
                FormId = s.FormId,
                FormName = s.Form.Name,
                Value = s.Code,
            };
        }
        private FormItemDTO Map(DformItemEntity s)
        {
            return new FormItemDTO
            {
                Id = s.Id,
                IsActive = true,
                Label = s.Label,
                Code = s.IdCode,
                FormId = s.FormId,
                FormName = s.Form.Name,
               FormItemTypeId = (int)s.FormItemType,
               FormItemType = s.FormItemType.ToString(),
               IsRequired = s.IsRequired,
               Order = s.Order,
               ValidationRegex = s.ValidationRegex,
               ValidationText = s.ValidationText,
               RespondentTypes = s.FormItemRespondentTypes.Select(b=>b.FormRespondentTypeId).ToList(),
               HelpText = s.HelpText,
               Section = s.Section,
            };
        }
        public TranferResponse<FormDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<FormDTO>();
            try
            {
                var data = _formRepository.QueryForm(query);
                var result = data.Result.Select(Map).ToList();
                response.Data.AddRange(result);
                response.Status = true;
                response.RecordCount = data.Count;
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                response.Status = false;

            }
            return response;
            
        }

        public TranferResponse<FormRespondentTypeDTO> QueryRespondentType(QueryBase query)
        {
            var response = new TranferResponse<FormRespondentTypeDTO>();
            try
            {
                var data = _formRepository.QueryFormRespondentType(query);
                var result = data.Result.Select(Map).ToList();
                response.Data.AddRange(result);
                response.Status = true;
                response.RecordCount = data.Count;
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                response.Status = false;

            }
            return response;
        }

        public TranferResponse<FormItemDTO> QueryItem(QueryBase query)
        {
            var response = new TranferResponse<FormItemDTO>();
            try
            {
                var data = _formRepository.QueryFormItem(query);
                var result = data.Result.Select(Map).ToList();
                response.Data.AddRange(result);
                response.Status = true;
                response.RecordCount = data.Count;
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                response.Status = false;

            }
            return response;
        }

        public BasicResponse Save(FormItemDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new DformItemEntity()
                {
                   
                    Id = dto.Id,
                    FormId = dto.FormId,
                    HelpText = dto.HelpText,
                    IdCode = "",
                    IsRequired = dto.IsRequired,
                    Label = dto.Label,
                    Section = dto.Section,
                    ValidationRegex = dto.ValidationRegex,
                    ValidationText = dto.ValidationText,
                    Order = dto.Order,
                    FormItemType = (DformItemType)dto.FormItemTypeId,
                    
                };
                _formRepository.SaveFormItem(entity);
               // entity = _formRepository.QueryFormItem(new QueryFormItem {Id = entity.Id}).Result.FirstOrDefault();
                _formRepository.DeleteFormItemRespondent(entity.Id);
                foreach(var respondent in dto.RespondentTypes)
                {
                    var respondentItem = new DformItemRespondentTypeEntity
                                             {
                                                 Id = dto.Id,
                                                 FormItemId = entity.Id,
                                                 FormRespondentTypeId = respondent
                                             };
                    _formRepository.SaveFormItemRespondent(respondentItem);

                }
                response.Status = true;
                response.Info = "Success";

            }

            catch (Exception ex)
            {
                response.Status = false;
                if (ex is DomainValidationException)
                {
                    var dex = ex as DomainValidationException;
                    response.Info = dex.FormatException();
                }
                else
                {
                    response.Status = false;
                    response.Info = ex.Message;
                }

            }
            return response;
        }
    }
}
