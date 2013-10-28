using System;
using System.Linq;
using TDR.Core.Domain.BI;
using TDR.Core.Domain.Clients;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.BI;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.BI.Impl
{
    public class ReportGroupItemService : IReportGroupItemService
    {
        private IReportGroupItemRepository _reportGroupItemRepository;

        public ReportGroupItemService(IReportGroupItemRepository reportGroupItemRepository)
        {
            _reportGroupItemRepository = reportGroupItemRepository;
        }

        public BasicResponse Save(ReportGroupItemDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new ReportGroupItem()
                {
                    Description = dto.Description,
                    Id = dto.Id,
                    Name = dto.Name,
                    GroupId  = dto.GroupId,
                    ReportOrder = dto.ReportOrder,
                    ReportUrl = dto.ReportUrl,
                    IsActive = true,
                };
                _reportGroupItemRepository.Save(entity);
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

        public ReportGroupItemDTO GetById(Guid id)
        {
            var entity = _reportGroupItemRepository.GetById(id);
            return Map(entity);
        }

        private ReportGroupItemDTO Map(ReportGroupItem entity)
        {
            if (entity == null)
                return null;
            var dto = new ReportGroupItemDTO
            {
                Description = entity.Description,
                GroupId = entity.GroupId,
                GroupName = entity.Group.Name,
                ReportOrder = entity.ReportOrder,
                ReportUrl = entity.ReportUrl,
                Id = entity.Id,
                IsActive = entity.IsActive,
                Name = entity.Name,
            };
            return dto;
        }

        public TranferResponse<ReportGroupItemDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<ReportGroupItemDTO>();
            try
            {
                var data = _reportGroupItemRepository.Query(query);
                var result = data.Result.OfType<ReportGroupItem>().Select(Map).ToList();
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
    }
}
