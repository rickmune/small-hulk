using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

using Smallhulk.Core.Domain;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;
using Smallhulk.Web.Lib.DTOBuilders;
using Smallhulk.Web.Lib.DTOS;

namespace Smallhulk.Web.Api
{
    public class MasterDataController : ApiController
    {
        private IDataTransferBuilder _dataTransferBuilder;

        public MasterDataController(IDataTransferBuilder dataTransferBuilder)
        {
            _dataTransferBuilder = dataTransferBuilder;
        }

        public IEnumerable<UserDTO> GetAllUsers()
        {
            var all = _dataTransferBuilder.GetAllUsers();
            return all;
        }
    }
}
