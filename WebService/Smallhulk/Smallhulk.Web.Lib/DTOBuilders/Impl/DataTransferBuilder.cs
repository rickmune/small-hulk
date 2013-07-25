using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Web.Lib.DTOS;
using Smallhulk.Web.Lib.Util;

namespace Smallhulk.Web.Lib.DTOBuilders.Impl
{
   public class DataTransferBuilder : IDataTransferBuilder
   {
       private IUserRepository _userRepository;
       private ICountryRepository _countryRepository;
       private IAccountRepository _accountRepository;
       private IProductRepository _productRepository;
       private ICategoryRepository _categoryRepository;
       private IRouteRepository _routeRepository;
       private IOutletRepository _outletRepository;

       public DataTransferBuilder(IUserRepository userRepository, ICountryRepository countryRepository, IAccountRepository accountRepository, IProductRepository productRepository, ICategoryRepository categoryRepository, IRouteRepository routeRepository, IOutletRepository outletRepository)
       {
           _userRepository = userRepository;
           _countryRepository = countryRepository;
           _accountRepository = accountRepository;
           _productRepository = productRepository;
           _categoryRepository = categoryRepository;
           _routeRepository = routeRepository;
           _outletRepository = outletRepository;
       }

       public TranferResponse<UserDTO> GetAllUsers(QueryMasterData query )
       {
           TranferResponse<UserDTO> response = new TranferResponse<UserDTO>();
           try
           {
               var data = _userRepository.Query(query);
               var result =data.Result.OfType<User>().Select(Map).ToList();
               response.Data.AddRange(result);
               response.RecordCount = data.Count;
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
                   response.Data.Add(Map(user));
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
                   RegistrationType = (RegistrationType)dto.RegistrationTypeId
                   
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

       public BasicResponse AddCategory(CategoryDTO dto)
       {
           BasicResponse response = new BasicResponse();
           try
           {
               var account = new Category()
               {
                   Name = dto.Name,
                   Id = dto.Id,
                   AccountId = dto.AccountId,
                   Description = dto.Description,
                   
                   IsActive = true,
                   CreatedOn = DateTime.Now,
                   UpdatedOn = DateTime.Now,
                  
               };

               _categoryRepository.Save(account);
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

       public BasicResponse AddProduct(ProductDTO dto)
       {
           BasicResponse response = new BasicResponse();
           try
           {
               var account = new Product()
               {
                   Name = dto.Name,
                   Id = dto.Id,
                   AccountId = dto.AccountId,
                   Description = dto.Description,
                   IsActive = true,
                   CreatedOn = DateTime.Now,
                   UpdatedOn = DateTime.Now,
                   CategoryId = dto.CategoryId,
                   BuyingPrice = dto.BuyingPrice,
                   SellingPrice = dto.SellingPrice,
               };

               _productRepository.Save(account);
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

       public BasicResponse AddRoute(RouteDTO dto)
       {
           throw new NotImplementedException();
       }

       public BasicResponse AddOutlet(OutletDTO dto)
       {
           throw new NotImplementedException();
       }

       public TranferResponse<AccountDTO> GetAccount(Guid accountid)
       {
           TranferResponse<AccountDTO> response = new TranferResponse<AccountDTO>();
           try
           {
               var data = _accountRepository.GetById(accountid);//.Query(new QueryMasterData()).Result.OfType<User>().Select(MapUserToUserDto).ToList();
               if(data==null)
               {
                   response.Info = "invalid account id";
                    response.Status = false;
                   return response;
               }
               response.Data.Add(Map(data));
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;
             
           }
           return response;
       }

       public TranferResponse<CategoryDTO> GetCategory(Guid accountid)
       {
           TranferResponse<CategoryDTO> response = new TranferResponse<CategoryDTO>();
           try
           {
               var data = _categoryRepository.Query(new QueryMasterData{AccountId = accountid}).Result.OfType<Category>().Select(Map).ToList();
               response.Data.AddRange(data);
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;

           }
           return response;
       }

       public TranferResponse<ProductDTO> GetProduct(Guid accountid)
       {
           TranferResponse<ProductDTO> response = new TranferResponse<ProductDTO>();
           try
           {
               var data = _productRepository.Query(new QueryMasterData { AccountId = accountid }).Result.OfType<Product>().Select(Map).ToList();
               response.Data.AddRange(data);
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;

           }
           return response;
       }

       public TranferResponse<RouteDTO> GetRoute(Guid accountid)
       {
           TranferResponse<RouteDTO> response = new TranferResponse<RouteDTO>();
           try
           {
               var data = _routeRepository.Query(new QueryMasterData { AccountId = accountid }).Result.OfType<Route>().Select(Map).ToList();
               response.Data.AddRange(data);
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;

           }
           return response;
       }

       public TranferResponse<OutletDTO> GetOutlet(Guid accountid)
       {
           TranferResponse<OutletDTO> response = new TranferResponse<OutletDTO>();
           try
           {
               var data = _outletRepository.Query(new QueryMasterData { AccountId = accountid }).Result.OfType<Outlet>().Select(Map).ToList();
               response.Data.AddRange(data);
               response.Status = true;
           }
           catch (Exception ex)
           {
               response.Info = ex.Message;
               response.Status = false;

           }
           return response;
       }

       public BasicResponse Register(RegisterDTO dto)
       {
           BasicResponse response = new BasicResponse();
           try
           {
               var account = new Account()
               {
                   Name = dto.Fullname,
                   Id = dto.AccountId,
                   IsActive = true,
                   CreatedOn = DateTime.Now,
                   UpdatedOn = DateTime.Now,
               };
               _accountRepository.Save(account);
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
                   UserType = (UserType)dto.UserTypeId,
                   RegistrationType = (RegistrationType)dto.RegistrationTypeId

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

       private static AccountDTO Map(Account s)
       {
           return new AccountDTO
           {
               Id = s.Id,
               Name = s.Name,
               IsActive = s.IsActive,
               


           };
       }
       private static CategoryDTO Map(Category s)
       {
           return new CategoryDTO
           {
               Id = s.Id,
               Name = s.Name,
               IsActive = s.IsActive,
               AccountId = s.AccountId,
               Description = s.Description,


           };
       }
       private static ProductDTO Map(Product s)
       {
           return new ProductDTO
           {
               Id = s.Id,
               Name = s.Name,
               IsActive = s.IsActive,
               AccountId = s.AccountId,
               Description = s.Description,
               BuyingPrice = s.BuyingPrice,
               SellingPrice = s.SellingPrice,
               CategoryId = s.CategoryId,

           };
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
       public  UserDTO Map(User s)
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
               AccountId = s.AccountId

           };
       }
       private static OutletDTO Map(Outlet s)
       {
           return new OutletDTO
           {
               Id = s.Id,
               Name = s.Name,
               Code = s.Code,
               IsActive = s.IsActive,
               AccountId = s.AccountId,
               Latitude = s.Latitude,
               Longitude = s.Longitude,
               RouteId = s.RouteId
           };
       }
       private static RouteDTO Map(Route s)
       {
           return new RouteDTO
           {
               Id = s.Id,
               Name = s.Name,
               Code = s.Code,
               IsActive = s.IsActive,
               AccountId = s.AccountId

           };
       }
    
   }
}
