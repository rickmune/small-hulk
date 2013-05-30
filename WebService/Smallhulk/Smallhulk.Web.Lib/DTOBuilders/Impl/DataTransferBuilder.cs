using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Web.Lib.DTOBuilders.Impl
{
   public class DataTransferBuilder : IDataTransferBuilder
   {
       private IUserRepository _userRepository;
       private ICountryRepository _countryRepository;

       public DataTransferBuilder(IUserRepository userRepository, ICountryRepository countryRepository)
       {
           _userRepository = userRepository;
           _countryRepository = countryRepository;
       }

       public TranferResponse<UserDTO> GetAllUsers()
       {
           TranferResponse<UserDTO> response = new TranferResponse<UserDTO>();
           try
           {
               var data =_userRepository.Query(new QueryMasterData()).Result.OfType<User>().Select(MapUserToUserDto).ToList();
               response.Data.AddRange(data);   
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;
               response.Info = "Success";
           }
           return response;
       }

       private static UserDTO MapUserToUserDto(User s)
       {
           return new UserDTO
                      {
                          Id = s.Id,
                          CountryId = s.CountryId,
                          Email = s.Email,
                          Fullname = s.Fullname,
                          Password = s.Password,
                          PhoneNumber = s.PhoneNumber,
                          IsActive = s.IsActive,
                          UserTypeId = (int)s.UserType,
                          Username = s.Username,

                      };
       }

       public TranferResponse<UserDTO> Login(string username, string password)
       {
           var response = new TranferResponse<UserDTO>();
           
           try
           {
               var user = _userRepository.Login(username, password);
               if (user != null)
               {
                   response.Data.Add(MapUserToUserDto(user));
                   response.Status = true;
                   response.Info = "Success";

               }
           }
           catch(Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;

           }
           return response;
       }

       public TranferResponse<CountryDTO> GetCountry()
       {
           TranferResponse<CountryDTO> response = new TranferResponse<CountryDTO>();
           try
           {
               var data = _countryRepository.Query(new QueryMasterData()).Result.OfType<Country>().Select(Map).ToList();
               response.Data.AddRange(data);
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;
               response.Info = "Success";
           }
           return response;
       }
       private static CountryDTO Map(Country s)
       {
           return new CountryDTO
           {
               Id = s.Id,
               Name = s.Name,
               ZipCode = s.ZipCode,
               Code = s.Code,
               IsActive = s.IsActive,
               

           };
       }

       public TranferResponse<UserDTO> GetAccount(Guid accountId)
       {
           throw new NotImplementedException();
       }

       public BasicResponse AddUser(UserDTO user)
       {
           throw new NotImplementedException();
       }
   }
}
