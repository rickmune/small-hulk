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
       private IAccountRepository _accountRepository;
       private IProductRepository _productRepository;
       private ICategoryRepository _categoryRepository;

       public DataTransferBuilder(IUserRepository userRepository, ICountryRepository countryRepository, IAccountRepository accountRepository, IProductRepository productRepository, ICategoryRepository categoryRepository)
       {
           _userRepository = userRepository;
           _countryRepository = countryRepository;
           _accountRepository = accountRepository;
           _productRepository = productRepository;
           _categoryRepository = categoryRepository;
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

       public BasicResponse AddAccount(AccountDTO dto)
       {
           BasicResponse response = new BasicResponse();
           try
           {
               var account = new Account()
                                 {
                                     Name = dto.Name,
                                     Id = dto.Id,
                                     IsActive = true,
                                     CreatedOn = DateTime.Now,
                                     UpdatedOn = DateTime.Now,
                                 };

               _accountRepository.Save(account);
               response.Status = true;
               response.Info = "Success";

           }
           catch (Exception ex)
           {

               response.Status = false;
               response.Info = ex.Message;
           }
           return response;
       }

       public BasicResponse AddUser(UserDTO dto)
       {
           BasicResponse response = new BasicResponse();
           try
           {
               var entity = new User()
               {
                   Username = dto.Username,
                   Id = dto.Id,
                   IsActive = true,
                   CreatedOn = DateTime.Now,
                   UpdatedOn = DateTime.Now,
                   AccountId = dto.AccountId,
                   Email = dto.Email,
                   Fullname = dto.Fullname,
                   Password = dto.Password,
                   PhoneNumber = dto.PhoneNumber,
                   UserType =(UserType) dto.UserTypeId,
                   
               };

               _userRepository.Save(entity);
               response.Status = true;
               response.Info = "Success";

           }
           catch (Exception ex)
           {

               response.Status = false;
               response.Info = ex.Message;
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
       private static UserDTO MapUserToUserDto(User s)
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

           };
       }

    
   }
}
