﻿using System;
using System.Collections.Generic;
using System.Data.Spatial;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using TDR.Core.Data.EF;
using TDR.Core.Domain;
using TDR.Core.Domain.Forms;
using TDR.Core.Util;
using TDR.EF;
using TDR.Models;
using TDR.WEB.LIB.DTOS;
using TDR.WEB.LIB.DTOS.Forms;
using TDR.WEB.LIB.Services.Forms;
using TDR.WEB.LIB.Util;

namespace TDR.API
{
    public class FormController  : ApiController
    {
        private TDRContext _context;
        private IFormService _formService;

        public FormController(TDRContext context, IFormService formService)
        {
            _context = context;
            _formService = formService;
        }

        //
        // GET: /Form/
        public TranferResponse<IDDTO> GetFormIds(Guid clientId)
        {
            var response = new TranferResponse<IDDTO>();
            try
            {
                response.Data =_context.Forms.Where(s => s.ClientId == clientId).Select(s => new IDDTO {Id = s.Id}).ToList();
                response.Status = true;
                
            }
            catch (Exception ex)
            {

                response.Status = false;
                response.Info = ex.Message;
            }
            return response;
        }

        public TranferResponse<Dform> GetForm(Guid formId)
        {
               var response = new TranferResponse<Dform>();
            try
            {
                
            var form = _context.Forms.FirstOrDefault(s => s.Id == formId);
            Dform f = new Dform();
           
           
            f.Name = form.Name;
            f.Id = form.Id;
            foreach (var respondentType in form.RespondentTypes)
            {
                DformRespondentType rt= new DformRespondentType();
                rt.Code = respondentType.Code;
                rt.Name = respondentType.Name;
                rt.Id = respondentType.Id;
                f.RespondentTypes.Add(rt);
            }
            foreach (var item in form.FormItems)
            {
                var i = new DformItem();
                i.Label = item.Label;
                i.IsRequired = item.IsRequired;
                i.Id = item.Id;
                i.FormItemType = item.FormItemType;
                i.Order = item.Order;
                i.ValidationRegex = item.ValidationRegex;
                i.ValidationText = item.ValidationText;
                foreach (var answers in item.FormItemAnswers)
                {
                    var a = new DformItemAnswer();
                    a.Id = answers.Id;
                    a.Text = answers.Text;
                    a.Value = answers.Value;
                    i.FormItemAnswer.Add(a);
                }
                foreach (var respondentType in item.FormItemRespondentTypes)
                {
                    var a = new DformItemRespondentType();
                    a.Id = respondentType.Id;
                    a.FormItemId = respondentType.FormItemId;
                    a.RespondentTypeId = respondentType.FormRespondentTypeId;
                    i.FormItemRespondentTypes.Add(a);
                }
                f.FormItems.Add(i);
            }
             
                response.Data = new List<Dform> { f };
            response.Status = true;

            }
            catch(Exception ex)
            {
                response.Status = false;
                response.Info = ex.Message;
            }

            return response;
        }

        public TranferResponse<FormRespondentTypeDTO> GetFormRespodent(Guid formId)
        {
            var response = new TranferResponse<FormRespondentTypeDTO>();
            try
            {
                response = _formService.QueryRespondentType(new QueryRespondentType {FormId = formId});
            }
            catch (Exception ex)
            {
                response.Status = false;
                response.Info = ex.Message;
            }

            return response;
        }
        public FormItemDTO GetFormItem(Guid itemId)
        {
           
            try
            {
              return  _formService.QueryItem(new QueryFormItem { Id = itemId }).Data.FirstOrDefault();
            }
            catch (Exception ex)
            {
                
            }

            return null;
        }

        public BasicResponse PublishFormResult(DformResult formresult)
        {
            BasicResponse result = new BasicResponse();
            try
            {
                if (formresult != null)
                {
                    DformResultEntity save = new DformResultEntity();
                    save.Id = formresult.Id;
                    save.FormId = formresult.FormId;
                    save.RespondentTypeId = formresult.RespondentTypeId;
                    save.DateInserted = DateTime.UtcNow;
                    save.Latitude = formresult.Latitude;
                    save.Longitude = formresult.Longitude;
                    save.Username = formresult.Username;
                    if (formresult.LocationId.HasValue)
                        save.LocationId = formresult.LocationId.Value;
                    _context.FormResult.Add(save);
                    foreach (var dformResultItem in formresult.FormResultItem)
                    {
                        foreach (var ans in dformResultItem.FormItemAnswer)
                        {
                            DformResultItemEntity item = new DformResultItemEntity();
                            item.Id =Guid.NewGuid();
                            item.FormItemId = dformResultItem.FormItemId;
                            item.FormResultId = save.Id;
                            item.FormItemAnswer = ans;
                           
                            _context.FormResultItem.Add(item);
                        }
                    }
                    _context.SaveChanges();
                    result.Info = "OK";
                    result.Status = true;
                }
                else
                {
                    result.Info = "Failed";
                    result.Status = false;
                }

            }
            catch (Exception ex)
            {
                result.Info = ex.Message;
               
            }
            return result;
        }
        [System.Web.Http.HttpPost]
        public BasicResponse SaveFormItem(FormItemDTO dto)
        {
            BasicResponse result = new BasicResponse();
            try
            {
               return _formService.Save(dto);
                  
               

            }
            catch (Exception ex)
            {
                result.Info = ex.Message;

            }
            return result;
        }
    }

    
}
