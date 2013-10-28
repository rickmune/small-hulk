using System;
using System.Linq;

using TDR.Core.Domain.Locations;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Locations;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Locations.Impl
{
    public class LocationService : ILocationService
    {
        private ILocationRepository _locationRepository;

        public LocationService(ILocationRepository locationRepository)
        {
            _locationRepository = locationRepository;
        }


        public BasicResponse Save(LocationDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new Location
                {
                    Code = dto.Code,
                    Id = dto.Id,
                    Name = dto.Name,
                    Description = dto.Description,
                    StructureId =(LocationStructure) dto.LocationStructureId,
                    IsActive = true,
                };
                _locationRepository.Save(entity);
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

        public LocationDTO GetById(Guid id)
        {
            var entity = _locationRepository.GetById(id);
            return Map(entity);
        }

        private LocationDTO Map(Location entity)
        {
            if (entity == null)
                return null;
            var dto = new LocationDTO
            {
                Code = entity.Code,
                Id = entity.Id,
                IsActive = entity.IsActive,
                Name = entity.Name,
                Description = entity.Description,
                LocationStructureId =(int) entity.StructureId,
                LocationStructureName = entity.StructureId.ToString(),
                
            };
            return dto;
        }

        public TranferResponse<LocationDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<LocationDTO>();
            try
            {
                var data = _locationRepository.Query(query);
                var result = data.Result.OfType<Location>().Select(Map).ToList();
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
