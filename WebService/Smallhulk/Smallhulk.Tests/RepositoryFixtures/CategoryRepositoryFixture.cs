﻿using System;
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
    class CategoryRepositoryFixture : BaseFixture
    {
        [Test]
        public override void CanSave()
        {
            var entity = AddCategory();
            Assert.IsNotNull(entity);
        }
         [Test]
        public override void CanGetById()
        {
            var entity = AddCategory();
             var entity2 = IocHelper.Using<ICategoryRepository>().GetById(entity.Id);
             Assert.IsNotNull(entity);
             Assert.AreEqual(entity.Name, entity2.Name);

        }
         [Test]
        public override void CanQuery()
        {
            var entity = AddCategory();
            var queryResult = IocHelper.Using<ICategoryRepository>().Query(new QueryMasterData { Name = entity.Name });
            Assert.IsNotNull(entity);
            Assert.IsTrue(queryResult.Count>0);
        }
    }
}
