using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TDR.Core.Domain.Users;
using TDR.Core.Repository;
using TDR.Core.Util;
using TDR.WEB.LIB.DTOS.Users;
using TDR.WEB.LIB.Util;

namespace TDR.WEB.LIB.Services.Users.Impl
{
    public class UserService : IUserService
    {
        private IUserRepository _userRepository;

        public UserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public TranferResponse<UserDTO> Login(string username, string password)
        {
            var response = new TranferResponse<UserDTO>();

            try
            {
                var user = _userRepository.Login(username, password);
                if (user != null)
                {
                    response.Data.Add(Map(user));
                    response.Status = true;
                    response.Info = "Success";

                }
            }
            catch (Exception ex)
            {
                response.Info = ex.Message;
                response.Status = false;

            }
            return response;
        }

        public bool CheckAvailablity(string username)
        {
          return  _userRepository.CheckUserAvailability(username);
        }

        public UserDTO Map(User s)
        {
            return new UserDTO
            {
                Id = s.Id,
                Email = s.Email,
                Fullname = s.Fullname,
                Password = s.Password,
                PhoneNumber = s.PhoneNumber,
                IsActive = s.IsActive,
                UserTypeId = (int)s.UserType,
                Username = s.Username,
                ClientId = s.ClientId,
                ClientName = s.ClientId.HasValue?s.Client.Name:"",
                UserType = s.UserType.ToString(),
                LocationId = s.LocationId,
                LocationName = s.LocationId.HasValue? s.Location.Name:""
            };
        }
        public BasicResponse Save(UserDTO dto)
        {
            var response = new BasicResponse();
            try
            {
                var entity = new User
                {

                    Id = dto.Id,
                    Username = dto.Username,
                    IsActive = true,
                    Email = dto.Email,
                    Fullname = dto.Fullname,
                    Password = dto.Password,
                    PhoneNumber = dto.PhoneNumber,
                    UserType = (UserType)dto.UserTypeId,
                    ClientId = dto.ClientId,
                    LocationId = dto.LocationId,
                   

                };
                _userRepository.Save(entity);
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

        public UserDTO GetById(Guid id)
        {
            var entity = _userRepository.GetById(id);
            return Map(entity);
        }

        public TranferResponse<UserDTO> Query(QueryBase query)
        {
            var response = new TranferResponse<UserDTO>();
            try
            {
                var data = _userRepository.Query(query);
                var result = data.Result.OfType<User>().Select(Map).ToList();
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
