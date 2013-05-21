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

       public DataTransferBuilder(IUserRepository userRepository)
       {
           _userRepository = userRepository;
       }

       public IEnumerable<UserDTO> GetAllUsers()
       {
           return _userRepository.Query(new QueryMasterData()).Result.OfType<User>().Select(s => new UserDTO
                                                                                      {
                                                                                          Id = s.Id,
                                                                                          CountryId = s.CountryId,
                                                                                          Email = s.Email,
                                                                                          Fullname = s.Fullname,
                                                                                          Password = s.Password,
                                                                                          PhoneNumber = s.PhoneNumber,
                                                                                          IsActive = s.IsActive,
                                                                                          UserType = s.UserType,
                                                                                          Username = s.Username,

                                                                                      });
       }
    }
}
