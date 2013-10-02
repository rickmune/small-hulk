using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Domain.Clients;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Clients;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Clients.Impl
{
    public class ClientService : IClientService
    {
        private IClientRepository _clientRepository;

        public ClientService(IClientRepository clientRepository)
        {
            _clientRepository = clientRepository;
        }

        public BasicResponse Save(ClientDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new Client
                {
                    Code = dto.Code,
                    Id = dto.Id,
                    Name = dto.Name,
                    IsActive = true,
                };
                _clientRepository.Save(entity);
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

        public ClientDTO GetById(Guid id)
        {
            var entity = _clientRepository.GetById(id);
            return Map(entity);
        }

        private ClientDTO Map(Client entity)
        {
            if (entity == null)
                return null;
            var dto = new ClientDTO
            {
                Code = entity.Code,
                Id = entity.Id,
                IsActive = entity.IsActive,
                Name = entity.Name,
            };
            return dto;
        }

        public TranferResponse<ClientDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<ClientDTO>();
            try
            {
                var data = _clientRepository.Query(query);
                var result = data.Result.OfType<Client>().Select(Map).ToList();
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
