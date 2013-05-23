using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NUnit.Framework;
using Smallhulk.Core.Repository;
using Smallhulk.Core.Util;

namespace Smallhulk.Tests.RepositoryFixtures
{
    [TestFixture]
    class UserRepositoryFixture : BaseFixture
    {
        [Test]
        public override void CanSave()
        {
            var entity = AddUser();
            Assert.IsNotNull(entity);
        }
         [Test]
        public override void CanGetById()
        {
            var entity = AddUser();
             var entity2 = IocHelper.Using<IUserRepository>().GetById(entity.Id);
             Assert.IsNotNull(entity);
             Assert.AreEqual(entity.Username, entity2.Username);

        }
         [Test]
        public override void CanQuery()
        {
            var entity = AddUser();
            var queryResult = IocHelper.Using<IUserRepository>().Query(new QueryMasterData { Name = entity.Username });
            Assert.IsNotNull(entity);
            Assert.IsTrue(queryResult.Count>0);
        }
    }
}
