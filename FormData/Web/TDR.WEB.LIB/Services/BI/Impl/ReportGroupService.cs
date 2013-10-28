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
    public class ReportGroupService : IReportGroupService
    {
        private IReportGroupRepository _reportGroupRepository;

        public ReportGroupService(IReportGroupRepository reportGroupRepository)
        {
            _reportGroupRepository = reportGroupRepository;
        }

        public BasicResponse Save(ReportGroupDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new ReportGroup
                {
                    Description = dto.Description,
                    Id = dto.Id,
                    Name = dto.Name,
                    ClientId  =dto.ClientId,
                    IsActive = true,
                };
                _reportGroupRepository.Save(entity);
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

        public ReportGroupDTO GetById(Guid id)
        {
            var entity = _reportGroupRepository.GetById(id);
            return Map(entity);
        }

        private ReportGroupDTO Map(ReportGroup entity)
        {
            if (entity == null)
                return null;
            var dto = new ReportGroupDTO
            {
                Description = entity.Description,
                ClientId = entity.ClientId,
                ClientName = entity.Client.Name,
                Id = entity.Id,
                IsActive = entity.IsActive,
                Name = entity.Name,
            };
            return dto;
        }

        public TranferResponse<ReportGroupDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<ReportGroupDTO>();
            try
            {
                var data = _reportGroupRepository.Query(query);
                var result = data.Result.OfType<ReportGroup>().Select(Map).ToList();
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
